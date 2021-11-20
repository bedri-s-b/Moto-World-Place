package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.entity.BaseEntity;
import com.example.motoworldplace.model.entity.MessageEntity;
import com.example.motoworldplace.model.view.MessageViewModel;
import com.example.motoworldplace.repository.MessageRepository;
import com.example.motoworldplace.service.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    public MessageServiceImpl(MessageRepository messageRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveMessage(MessageEntity message) {
        messageRepository.save(message);
    }

    @Override
    public List<MessageViewModel> getAllMessageFromUser(String principal) {

        List<MessageViewModel> fromUser = messageRepository.findByFromUser_Username(principal).stream()
                .map(message -> {
                    MessageViewModel map = modelMapper.map(message, MessageViewModel.class);
                    map.setToUserPicture(message.getToUser().getPicture().getUrl());
                    return map;
                }).collect(Collectors.toList());


        return
                fromUser.stream().sorted(Comparator.comparing(MessageViewModel::getTime).reversed()).collect(Collectors.toList());


    }

    @Override
    public List<MessageViewModel> getAllMessagesToUser(String username) {
        List<MessageViewModel> toUser = messageRepository.findByToUser_Username(username).stream()
                .map(message -> {
                    MessageViewModel map = modelMapper.map(message, MessageViewModel.class);
                    map.setToUserPicture(message.getFromUser().getPicture().getUrl());
                    return map;
                }).collect(Collectors.toList());

        return toUser.stream().sorted(Comparator.comparing(MessageViewModel::getTime).reversed()).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public MessageViewModel findMessage(Long id) {
        return messageRepository.findById(id).
                map(message -> {
                    message.setReadMessage(true);
                    return modelMapper.map(message, MessageViewModel.class);
                }).orElseThrow();
    }

    @Override
    public boolean isOwnerOnMessages(String username, Long id) {
        List<Long> byUserSend = messageRepository.findByToUser_Username(username)
                .stream().map(BaseEntity::getId).collect(Collectors.toList());

        List<Long> collect = messageRepository.findByFromUser_Username(username).stream().map(BaseEntity::getId).collect(Collectors.toList());

        collect.addAll(byUserSend);

        return collect.contains(id);
    }
}
