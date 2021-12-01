package com.example.motoworldplace.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EventServiceModel {

    private String title;
    private String description;
    private LocalDateTime stared;
    private String creator;
    private String group;
    private List<String> members = new ArrayList<>();

    public EventServiceModel() {
    }

    public EventServiceModel(String title, String description, LocalDateTime stared, String creator, String group) {
        this.title = title;
        this.description = description;
        this.stared = stared;
        this.creator = creator;
        this.group = group;
    }

    public String getTitle() {
        return title;
    }

    public EventServiceModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getStared() {
        return stared;
    }

    public EventServiceModel setStared(LocalDateTime stared) {
        this.stared = stared;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public EventServiceModel setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public EventServiceModel setGroup(String group) {
        this.group = group;
        return this;
    }

    public List<String> getMembers() {
        return members;
    }

    public EventServiceModel setMembers(List<String> members) {
        this.members = members;
        return this;
    }
}
