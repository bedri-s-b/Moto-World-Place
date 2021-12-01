package com.example.motoworldplace.model.service;

public class UserEventServiceModel {

    private Long id;
    private String username;

    public Long getId() {
        return id;
    }

    public UserEventServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserEventServiceModel setUsername(String username) {
        this.username = username;
        return this;
    }
}
