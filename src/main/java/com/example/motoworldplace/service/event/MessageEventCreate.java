package com.example.motoworldplace.service.event;

import com.example.motoworldplace.service.MessageService;
import org.springframework.context.ApplicationEvent;

public class MessageEventCreate extends ApplicationEvent {
    private final String fromUser;
    private final String toUser;

    public MessageEventCreate(Object source, String fromUser, String toUser) {
        super(source);
        this.fromUser = fromUser;
        this.toUser = toUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }
}
