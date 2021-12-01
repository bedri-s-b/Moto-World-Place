package com.example.motoworldplace.service;

import com.example.motoworldplace.model.binding.EventBindingModel;
import com.example.motoworldplace.model.service.EventServiceModel;
import com.example.motoworldplace.model.view.EventViewModel;

import java.security.Principal;
import java.util.List;

public interface EventService {
    EventServiceModel addEvent(EventBindingModel eventBindingModel);


    List<EventViewModel> findAllEvents();

    EventViewModel findEventWithId(Long idEvent, Principal principal);

    boolean isCreatorEvent(String userName, Long id);

    void addMember(Long idEvent, String name);
}
