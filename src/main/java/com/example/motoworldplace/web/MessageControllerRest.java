package com.example.motoworldplace.web;

import com.example.motoworldplace.model.view.MessageViewModel;
import com.example.motoworldplace.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageControllerRest {
    private final MessageService messageService;

    public MessageControllerRest(MessageService messageService) {
        this.messageService = messageService;
    }


    @GetMapping("/users/messages/rest/all")
    public ResponseEntity<List<MessageViewModel>> getMessages(@AuthenticationPrincipal User user){
        List<MessageViewModel> allMessages = messageService.getAllMessageFromUser(user.getUsername());
        return ResponseEntity.ok(allMessages);
    }
}
