package com.example.motoworldplace.model.service;

import com.example.motoworldplace.model.entity.enums.CityEnum;
import com.example.motoworldplace.model.view.GroupViewModel;

import java.util.HashSet;
import java.util.Set;

public class UserServiceModel {

    private Long id;
    private String username;
    private String fullName;
    private String password;
    private String email;
    private Integer age;
    private CityEnum city;
    private String picture;
    private Set<GroupViewModel> groups = new HashSet<>();
    private boolean isOwner;

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

    public CityEnum getCity() {
        return city;
    }

    public UserServiceModel setCity(CityEnum city) {
        this.city = city;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public UserServiceModel setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public Set<GroupViewModel> getGroups() {
        return groups;
    }

    public UserServiceModel setGroups(Set<GroupViewModel> groups) {
        this.groups = groups;
        return this;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public UserServiceModel setOwner(boolean owner) {
        isOwner = owner;
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
