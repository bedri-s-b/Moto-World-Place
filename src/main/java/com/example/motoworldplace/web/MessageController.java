package com.example.motoworldplace.web;

import com.example.motoworldplace.model.view.MessageViewModel;
import com.example.motoworldplace.service.MessageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping("/users/messages/all")
    public String allMessages(@AuthenticationPrincipal User user, Model model) {

        List<MessageViewModel> messagesFromUser = messageService.getAllMessageFromUser(user.getUsername());
        List<MessageViewModel> messagesToUser = messageService.getAllMessagesToUser(user.getUsername());
        model.addAttribute("messagesFromUser", messagesFromUser);
        model.addAttribute("messagesToUser", messagesToUser);

        return "all-messages";
    }
    @GetMapping("/users/messages/outbox/{id}")
    public String inBoxMessage(@PathVariable Long id, Model model, Principal principal) {

        if (messageService.isOwnerOnMessages(principal.getName(),id)){
            model.addAttribute("message", messageService.findMessage(id));
        }else {
            throw new RuntimeException("Invalid id");
        }
        return "outbox-message";
    }

    @GetMapping("/users/messages/inbox/{id}")
    public String outBoxMessage(@PathVariable Long id, Model model,Principal principal) {
        if (messageService.isOwnerOnMessages(principal.getName(),id)){
            model.addAttribute("message", messageService.findMessage(id));
        }else {
            throw new RuntimeException("Invalid id");
        }
        return "inbox-message";
    }
}
