package com.example.motoworldplace.model.entity;

import com.example.motoworldplace.model.entity.enums.RoleEnum;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{

    private String username;
    private String fullName;
    private String email;
    private String password;
    private Integer age;
    private CityEntity city;
    private PictureEntity picture;
    private Set<GroupEntity> group = new HashSet<>();
    private Set<MessageEntity> receivedMessages = new HashSet<>();
    private Set<MessageEntity> sendMessages = new HashSet<>();
    private RoleEnum role;

    public UserEntity() {
    }

    @Column(unique = true,nullable = false,length = 20)
    public String getUsername() {
        return username;
    }

    public UserEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    @Column(nullable = false,length = 20)
    public String getFullName() {
        return fullName;
    }

    public UserEntity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Column(nullable = false)
    public Integer getAge() {
        return age;
    }

    public UserEntity setAge(Integer age) {
        this.age = age;
        return this;
    }


    @ManyToMany(fetch = FetchType.EAGER)
    public Set<GroupEntity> getGroup() {
        return group;
    }

    public UserEntity setGroup(Set<GroupEntity> group) {
        this.group = group;
        return this;
    }

    @OneToMany(mappedBy = "fromUser",fetch = FetchType.EAGER)
    public Set<MessageEntity> getReceivedMessages() {
        return receivedMessages;
    }

    public UserEntity setReceivedMessages(Set<MessageEntity> receivedMessages) {
        this.receivedMessages = receivedMessages;
        return this;
    }


    @OneToMany(mappedBy = "toUser",fetch = FetchType.EAGER)
    public Set<MessageEntity> getSendMessages() {
        return sendMessages;
    }

    public UserEntity setSendMessages(Set<MessageEntity> sendMessages) {
        this.sendMessages = sendMessages;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @ManyToOne
    public CityEntity getCity() {
        return city;
    }
    public UserEntity setCity(CityEntity city) {
        this.city = city;
        return this;
    }

    @ManyToOne
    public PictureEntity getPicture() {
        return picture;
    }

    public UserEntity setPicture(PictureEntity pictureUrl) {
        this.picture = pictureUrl;
        return this;
    }

    @Enumerated(EnumType.STRING)
    public RoleEnum getRole() {
        return role;
    }

    public UserEntity setRole(RoleEnum role) {
        this.role = role;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }
}
