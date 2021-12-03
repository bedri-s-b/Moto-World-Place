package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.binding.GroupAddBindingModel;
import com.example.motoworldplace.model.entity.GroupEntity;
import com.example.motoworldplace.model.entity.MessageEntity;
import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import com.example.motoworldplace.model.entity.enums.GroupEnum;
import com.example.motoworldplace.model.entity.enums.RoleEnum;
import com.example.motoworldplace.model.view.GroupViewModel;
import com.example.motoworldplace.model.view.UserViewModel;
import com.example.motoworldplace.repository.GroupRepository;
import com.example.motoworldplace.repository.MessageRepository;
import com.example.motoworldplace.repository.UserRepository;
import com.example.motoworldplace.service.EventService;
import com.example.motoworldplace.service.GroupService;
import com.example.motoworldplace.service.PictureService;
import com.example.motoworldplace.service.UserService;
import com.example.motoworldplace.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.motoworldplace.constans.ConstantsUrl.DEFAULT_MESSAGE_GROUP;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final UserService userService;
    private final PictureService pictureService;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MotoWorldUserServiceImpl motoWorldUserService;
    private final EventService eventService;

    public GroupServiceImpl(GroupRepository groupRepository, UserService userService, PictureService pictureService, ModelMapper modelMapper, UserRepository userRepository, MessageRepository messageRepository, MotoWorldUserServiceImpl motoWorldUserService, EventService eventService) {
        this.groupRepository = groupRepository;
        this.userService = userService;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.motoWorldUserService = motoWorldUserService;
        this.eventService = eventService;
    }

    @Override
    public void initGroup() {
        if (groupRepository.count() != 0) {
            return;
        }
        GroupEntity group = new GroupEntity().setName(GroupEnum.FREE_RAIDER.name())
                .setAdmin(userService.findByUserName("Admin")).setPicture(pictureService.getDefaultGroupPic());
        groupRepository.save(group);
        userService.addUser(userService.findByUserName("Admin"), group);
    }

    @Override
    public List<GroupViewModel> findAllGroup(Principal principal) {

        return groupRepository.findAll().stream()
                .map(groupEntity -> map(groupEntity,groupEntity.getAdmin().getUsername())).collect(Collectors.toList());
    }

    @Override
    public void addGroup(GroupAddBindingModel groupAddBindingModel, String username) throws IOException {

        GroupEntity groupEntity = modelMapper.map(groupAddBindingModel, GroupEntity.class);
        UserEntity currentUser = userService.findByUserName(username);
        currentUser.setGroupEnum(RoleEnum.GROUP_ADMIN);
        groupEntity.setAdmin(currentUser);
        groupEntity.getMembers().add(currentUser);
        String newPicture = pictureService.createNewPicture(groupAddBindingModel.getPicture(), currentUser.getId());
        PictureEntity currentPicture = pictureService.findByPublicId(newPicture);
        groupEntity.setPicture(currentPicture);
        GroupEntity saveGroup = groupRepository.save(groupEntity);
        currentUser.getGroups().add(saveGroup);
        userRepository.save(currentUser);

        UserDetails principal = motoWorldUserService.loadUserByUsername(currentUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                currentUser.getPassword(),
                principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Override
    public boolean findByName(String name) {
        return groupRepository.existsByName(name);
    }


    @Override
    public GroupViewModel findGroupViewModelById(Long id, UserViewModel userViewModel) {
        GroupEntity group = groupRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id));
        return new GroupViewModel()
                .setName(group.getName())
                .setAdmin(group.getAdmin().getUsername())
                .setPicture(group.getPicture().getUrl())
                .setCreated(group.getCreated())
                .setMembers(group.getMembers().size())
                .setId(group.getId())
                .setMembersName(group.getMembers().stream().map(UserEntity::getUsername).collect(Collectors.toSet()));
    }

    @Override
    public void joinMemberToGroup(Long idGroup, String currentUser, String otherNameUser) {
        GroupEntity groupEntity = groupRepository.findById(idGroup).get();

        UserEntity currentU = userService.findByUserName(currentUser);
        UserEntity otherU = userService.findByUserName(otherNameUser);
        // save group
        currentU.getGroups().add(groupEntity);
        userRepository.saveAndFlush(currentU);

        MessageEntity message = new MessageEntity().setFromUser(currentU).setToUser(otherU)
                .setMessage(String.format(DEFAULT_MESSAGE_GROUP, groupEntity.getName())).setReadMessage(false);
        messageRepository.save(message);

    }

    @Override
    public List<GroupViewModel> findAllGroupWhichAdmin(String username) {
        return groupRepository.findByAdmin_Username(username).stream()
                .map(u -> map(u, username)).collect(Collectors.toList());
    }


    private GroupViewModel map(GroupEntity groupEntity, String username) {

        GroupViewModel groupViewModel = new GroupViewModel();
        groupViewModel.setName(groupEntity.getName());
        groupViewModel.setCanJoin(isMember(username, groupEntity.getId()));
        groupViewModel.setMembers(groupEntity.getMembers().size());
        groupViewModel.setAdmin(groupEntity.getAdmin().getUsername());
        groupViewModel.setPicture(groupEntity.getPicture().getUrl());
        groupViewModel.setCreated(groupEntity.getCreated());
        groupViewModel.setId(groupEntity.getId());
        groupViewModel.setEventViewModel(eventService.findMostSoonEvent(groupEntity.getName()));
        return groupViewModel;
    }

    @Override
    public boolean isMember(String username, Long id) {


        GroupEntity group = groupRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id));

        UserEntity user = userRepository.findByUsername(username).orElseThrow(() -> new ObjectNotFoundException(id));
        if (user.getRole().equals(RoleEnum.ADMIN)){
            return true;
        }

        if (group == null) {
            return false;
        } else {
            for (UserEntity m : group.getMembers()) {
                if (m.getUsername().equals(username)) {
                    return true;
                }

            }
        }
        return false;
    }

    @Override
    public String findNameById(Long id) {
        return groupRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id)).getName();
    }

    @Override
    public GroupViewModel checkExistGroup(Long id) {
        GroupEntity groupEntity = groupRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(id));
        return map(groupEntity,groupEntity.getAdmin().getUsername());
    }


}
