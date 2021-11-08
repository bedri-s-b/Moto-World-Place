package com.example.motoworldplace.model.service;

import com.example.motoworldplace.model.entity.GroupEntity;
import com.example.motoworldplace.model.entity.MessageEntity;
import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.model.entity.enums.CityEnum;

import java.util.HashSet;
import java.util.Set;

public class UserServiceModel {

    private Long id;
    private String username;
    private String fullName;
    private String email;
    private Integer age;
    private CityEnum city;
    private String password;
    private PictureEntity picture;
    private Set<GroupEntity> group = new HashSet<>();
    private Set<MessageEntity> receivedMessages = new HashSet<>();

    public UserServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public UserServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserServiceModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public UserServiceModel setPicture(PictureEntity picture) {
        this.picture = picture;
        return this;
    }

    public Set<GroupEntity> getGroup() {
        return group;
    }

    public UserServiceModel setGroup(Set<GroupEntity> group) {
        this.group = group;
        return this;
    }

    public Set<MessageEntity> getReceivedMessages() {
        return receivedMessages;
    }

    public UserServiceModel setReceivedMessages(Set<MessageEntity> receivedMessages) {
        this.receivedMessages = receivedMessages;
        return this;
    }

    public CityEnum getCity() {
        return city;
    }

    public UserServiceModel setCity(CityEnum city) {
        this.city = city;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserServiceModel setPassword(String password) {
        this.password = password;
        return this;
    }
}
