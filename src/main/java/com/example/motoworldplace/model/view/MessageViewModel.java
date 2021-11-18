package com.example.motoworldplace.model.view;

import com.example.motoworldplace.model.entity.UserEntity;

import java.time.LocalDateTime;

public class MessageViewModel {

    private Long id;
    private String message;
    private String fromUser;
    private String toUser;
    private String time;
    private Boolean readMessage;
    private String toUserPicture;
    private String fromUserPicture;

    public Long getId() {
        return id;
    }

    public MessageViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public MessageViewModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getFromUser() {
        return fromUser;
    }

    public MessageViewModel setFromUser(String fromUser) {
        this.fromUser = fromUser;
        return this;
    }

    public String getToUser() {
        return toUser;
    }

    public MessageViewModel setToUser(String toUser) {
        this.toUser = toUser;
        return this;
    }

    public String getTime() {
        return time;
    }

    public MessageViewModel setTime(String time) {
        this.time = time;
        return this;
    }

    public Boolean getReadMessage() {
        return readMessage;
    }

    public MessageViewModel setReadMessage(Boolean readMessage) {
        this.readMessage = readMessage;
        return this;
    }

    public String getToUserPicture() {
        return toUserPicture;
    }

    public MessageViewModel setToUserPicture(String toUserPicture) {
        this.toUserPicture = toUserPicture;
        return this;
    }

    public String getFromUserPicture() {
        return fromUserPicture;
    }

    public MessageViewModel setFromUserPicture(String fromUserPicture) {
        this.fromUserPicture = fromUserPicture;
        return this;
    }
}
