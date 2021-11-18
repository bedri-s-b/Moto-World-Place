package com.example.motoworldplace.service;

import com.example.motoworldplace.model.entity.MessageEntity;
import com.example.motoworldplace.model.view.MessageViewModel;

import java.util.List;

public interface MessageService {

    void saveMessage(MessageEntity message);

    List<MessageViewModel> getAllMessageFromUser(String principal);

    List<MessageViewModel> getAllMessagesToUser(String username);

    MessageViewModel findMessage(Long id);

    boolean isOwnerOnMessages(String username,Long id);
}
