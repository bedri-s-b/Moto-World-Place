package com.example.motoworldplace.model.entiy;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class MessageEntity extends BaseEntity {

    private String message;
    private UserEntity fromUser;
    private UserEntity toUser;
    private LocalDateTime time;
    private Boolean readMessage;

    public MessageEntity() {
    }

    @Column(columnDefinition = "longtext",nullable = false)
    public String getMessage() {
        return message;
    }

    public MessageEntity setMessage(String message) {
        this.message = message;
        return this;
    }

    @ManyToOne
    public UserEntity getFromUser() {
        return fromUser;
    }

    public MessageEntity setFromUser(UserEntity fromUser) {
        this.fromUser = fromUser;
        return this;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public MessageEntity setTime(LocalDateTime time) {
        this.time = time;
        return this;
    }


    public Boolean getReadMessage() {
        return readMessage;
    }

    public MessageEntity setReadMessage(Boolean readMessage) {
        this.readMessage = readMessage;
        return this;
    }

    @PrePersist
    public void beforeCreate() {
        System.out.println("BEFORE CREATE!");
        setTime(LocalDateTime.now().plusHours(3));
    }

    @ManyToOne
    public UserEntity getToUser() {
        return toUser;
    }

    public MessageEntity setToUser(UserEntity toUser) {
        this.toUser = toUser;
        return this;
    }
}
