package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.binding.EventBindingModel;
import com.example.motoworldplace.model.entity.EventEntity;
import com.example.motoworldplace.model.entity.GroupEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import com.example.motoworldplace.model.entity.enums.RoleEnum;
import com.example.motoworldplace.model.service.EventServiceModel;
import com.example.motoworldplace.model.service.UserEventServiceModel;
import com.example.motoworldplace.model.view.EventViewModel;
import com.example.motoworldplace.repository.EventRepository;
import com.example.motoworldplace.repository.GroupRepository;
import com.example.motoworldplace.repository.UserRepository;
import com.example.motoworldplace.service.EventService;
import com.example.motoworldplace.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public EventServiceImpl(EventRepository eventRepository, ModelMapper modelMapper, UserRepository userRepository, GroupRepository groupRepository) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    @Override
    public EventServiceModel addEvent(EventBindingModel eventBindingModel) {
        UserEntity userEntity = userRepository.findByUsername(eventBindingModel.getCreator()).orElseThrow();
        GroupEntity groupEntity = groupRepository.findByName(eventBindingModel.getGroup());
        EventEntity eventEntity = modelMapper.map(eventBindingModel, EventEntity.class);
        eventEntity.setCreator(userEntity).setGroup(groupEntity).setStared(eventBindingModel.getPresentOrFuture()).getMembersCome().add(userEntity);
        eventRepository.save(eventEntity);
        EventServiceModel eventServiceModel = modelMapper.map(eventBindingModel, EventServiceModel.class).setStared(eventBindingModel.getPresentOrFuture());
        eventServiceModel.getMembers().add(userEntity.getUsername());
        return eventServiceModel;
    }

    @Override
    public List<EventViewModel> findAllEvents() {
        return eventRepository.findAll().stream().map(this::mapView).collect(Collectors.toList());
    }


    @Transactional
    @Override
    public EventViewModel findEventWithId(Long idEvent, Principal principal) {
        EventViewModel eventViewModel = mapView(eventRepository.findByIdCurrent(idEvent).orElseThrow(() -> new ObjectNotFoundException(idEvent)));
        eventViewModel.setMemberEvent(eventViewModel
                .getMembersCome()
                .stream()
                .anyMatch(a -> a.getUsername().equals(principal.getName())));
        eventViewModel.setCanDelete(eventViewModel.getCreator().equals(principal.getName()) ||
                userRepository.findByUsername(principal.getName()).orElseThrow(()-> new ObjectNotFoundException(principal.getName())).getRole().equals(RoleEnum.ADMIN));

        return eventViewModel;


    }

    @Override
    public boolean isCreatorEvent(String userName, Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id)).getCreator().getUsername().equals(userName);
    }

    @Override
    public void addMember(Long idEvent, String name) {
        EventEntity eventEntity = eventRepository.findByIdCurrent(idEvent).orElseThrow(() -> new ObjectNotFoundException(idEvent));
        UserEntity userEntity = userRepository.findByUsername(name).orElseThrow(() -> new ObjectNotFoundException(name));
        eventEntity.getMembersCome().add(userEntity);
        eventRepository.save(eventEntity);
    }

    @Override
    public void deleteEvent(Long idEvent, String name) {
        eventRepository.deleteById(idEvent);
    }

    @Transactional
    @Override
    public EventViewModel findMostSoonEvent(String groupName) {
        var events = eventRepository.findEventsByGroupNameOrderByStared(groupName);
        if (events.isEmpty()){
            return new EventViewModel().setTitle("Creat firs event in this group")
                    .setStarted("Now ;)");
        }
        return mapView(events.get(0));
    }

    @Transactional
    @Override
    public List<EventViewModel> findAllEventsOnThisGroup(String name) {
        var all = eventRepository.findEventsByGroupNameOrderByStared(name);
        return all.stream().map(this::mapView).collect(Collectors.toList());
    }

    private EventViewModel mapView(EventEntity eventEntity) {

        EventViewModel eventViewModel = new EventViewModel(
                eventEntity.getId(),
                eventEntity.getTitle(),
                eventEntity.getDescription(),
                eventEntity.getStared().format(DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm")),
                eventEntity.getCreator().getUsername(),
                eventEntity.getGroup().getName()

        );
        eventViewModel.setMembersCome(eventEntity.getMembersCome().stream().map(e -> modelMapper.map(e, UserEventServiceModel.class)).collect(Collectors.toSet()));
        return eventViewModel;
    }
}
