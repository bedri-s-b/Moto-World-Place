package com.example.motoworldplace.web;

import com.example.motoworldplace.model.binding.EventBindingModel;
import com.example.motoworldplace.model.service.EventServiceModel;
import com.example.motoworldplace.model.view.EventViewModel;
import com.example.motoworldplace.service.EventService;
import com.example.motoworldplace.service.GroupService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EventsController {

    private final GroupService groupService;
    private final EventService eventService;

    public EventsController(GroupService groupService, EventService eventService) {
        this.groupService = groupService;
        this.eventService = eventService;
    }

    @GetMapping("/groups/{idGroup}/group/events")
    public String getAllEvents(@PathVariable("idGroup") Long id, Model model) {
        model.addAttribute("idGroup", id);
        List<EventViewModel> events = eventService.findAllEvents();
        model.addAttribute("events", events);
        return "events";
    }

    @GetMapping("/groups/{idGroup}/group/events/add")
    public String addEvents(@PathVariable("idGroup") Long id, Model model) {
        model.addAttribute("idGroup", id);
        return "add-event";
    }

    @PostMapping("/groups/{idGroup}/group/events/add")
    public String addEvent(@PathVariable("idGroup") Long id,
                           @Valid EventBindingModel eventBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal) {
        eventBindingModel.setPresentOrFuture(LocalDateTime.parse(eventBindingModel.getStarted()));
        eventBindingModel.setGroup(groupService.findNameById(id))
                .setCreator(principal.getName());
        boolean before = LocalDateTime.now().isAfter(eventBindingModel.getPresentOrFuture());
        if (bindingResult.hasErrors() || before) {
            redirectAttributes.addFlashAttribute("eventBindingModel", eventBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.eventBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("before", before);
            return "redirect:/groups/" + id + "/group/events/add/error";
        }

        EventServiceModel eventServiceModel = eventService.addEvent(eventBindingModel);
        return "redirect:/groups/" + id + "/group/events";
    }


    //    @PreAuthorize("isCreatorEvent(#idEvent)")
    @GetMapping("/groups/{idGroup}/group/events/{idEvent}")
    public String getEvent(@PathVariable("idGroup") Long idGroup, @PathVariable("idEvent") Long idEvent, Model model, Principal principal) {
        EventViewModel event = eventService.findEventWithId(idEvent, principal);
        model.addAttribute("event", event);
        return "event";
    }


    @GetMapping("/groups/{idGroup}/group/events/add/error")
    public String addEventsError(@PathVariable("idGroup") Long id) {
        return "add-event";
    }

    @PostMapping("/groups/{idGroup}/group/events/{idEvent}")
    public String joinAs(@PathVariable("idGroup") Long idGroup,
                         @PathVariable("idEvent") Long idEvent,
                         Model model,
                         Principal principal) {
        model.addAttribute("idGroup", idGroup).addAttribute("idEvent", idEvent);
        eventService.addMember(idEvent, principal.getName());
        return "redirect:/groups/" + idGroup + "/group/events/" + idEvent;
    }

    @ModelAttribute
    public EventBindingModel eventBindingModel() {
        return new EventBindingModel();
    }


}
