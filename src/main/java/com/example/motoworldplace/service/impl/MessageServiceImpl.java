package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.entiy.MessageEntity;
import com.example.motoworldplace.repository.MessageRepository;
import com.example.motoworldplace.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void saveMessage(MessageEntity message) {
        messageRepository.save(message);
    }
}
