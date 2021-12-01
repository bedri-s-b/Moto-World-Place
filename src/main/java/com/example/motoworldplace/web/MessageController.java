package com.example.motoworldplace.web;

import com.example.motoworldplace.model.binding.UserMessageBindingModel;
import com.example.motoworldplace.model.view.MessageViewModel;
import com.example.motoworldplace.model.view.UserViewModel;
import com.example.motoworldplace.service.MessageService;
import com.example.motoworldplace.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class MessageController {

    private final MessageService messageService;
    public final UserService userService;


    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }


    @GetMapping("/users/messages/all")
    public String allMessages(@AuthenticationPrincipal User user, Model model) {

        List<MessageViewModel> messagesFromUser = messageService.getAllMessageFromUser(user.getUsername());
        List<MessageViewModel> messagesToUser = messageService.getAllMessagesToUser(user.getUsername());
        model.addAttribute("messagesFromUser", messagesFromUser);
        model.addAttribute("messagesToUser", messagesToUser);

        return "all-messages";
    }


    @PreAuthorize("isOwnerOnMessages(#id)")
    @GetMapping("/users/messages/outbox/{id}")
    public String outBoxMessage(@PathVariable Long id, Model model) {
        model.addAttribute("message", messageService.findMessageOut(id));
        return "outbox-message";
    }

    @PreAuthorize("isOwnerOnMessages(#id)")
    @GetMapping("/users/messages/inbox/{id}")
    public String inBoxMessage(@PathVariable Long id, Model model) {
        model.addAttribute("message", messageService.findMessageIn(id));
        return "inbox-message";
    }

    @PreAuthorize("isOwnerOnMessages(#id)")
    @DeleteMapping("/users/messages/inbox/{id}")
    public String deleteMessageIn(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return "redirect:/users/messages/all";
    }

    @PreAuthorize("isOwnerOnMessages(#id)")
    @DeleteMapping("/users/messages/outbox/{id}")
    public String deleteMessageOut(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return "redirect:/users/messages/all";
    }


    @GetMapping("/users/message/{id}/send")
    public String getMessageForm(@PathVariable Long id, @AuthenticationPrincipal UserDetails user, Model model) {
        UserViewModel currentUser = userService.findUserViewModelByUsername(user.getUsername());
        String toUsername = userService.findById(id).orElseThrow().getUsername();
        UserViewModel toUser = userService.findUserViewModelByUsername(toUsername);
        model.addAttribute("current", currentUser);
        model.addAttribute("toUser", toUser);
        return "message-send";
    }


    @PostMapping("/users/message/{id}/send")
    public String sentMessageToUser(@PathVariable("id") Long id, Principal principal,
                                    @Valid UserMessageBindingModel userMessageBindingModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userMessageBindingModel", userMessageBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userMessageBindingModel", bindingResult);
            return "redirect:/users/message/" + id + "/send/error";
        }

        messageService.saveThisMessage(id, principal.getName(), userMessageBindingModel.getMessage());

        return "redirect:/users/messages/all";
    }

    @GetMapping("/users/message/{id}/send/error")
    public String sendMessageError(@PathVariable("id") Long id, Principal principal, Model model) {
        UserViewModel currentUser = userService.findUserViewModelByUsername(principal.getName());
        String toUsername = userService.findById(id).orElseThrow().getUsername();
        UserViewModel toUser = userService.findUserViewModelByUsername(toUsername);
        model.addAttribute("current", currentUser);
        model.addAttribute("toUser", toUser);
        return "message-send";
    }

    @ModelAttribute
    public UserMessageBindingModel userMessageBindingModel() {
        return new UserMessageBindingModel();
    }
}
