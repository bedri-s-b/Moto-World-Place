package com.example.motoworldplace.model.view;

import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.model.entity.UserEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class GroupViewModel {

    private Long id;
    private String name;
    private String admin;
    private Integer members;
    private Set<String> membersName = new HashSet<>();
    private String picture;
    private LocalDate created;
    private EventViewModel eventViewModel;
    private boolean canJoin;

    public Long getId() {
        return id;
    }

    public GroupViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public GroupViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getAdmin() {
        return admin;
    }

    public GroupViewModel setAdmin(String admin) {
        this.admin = admin;
        return this;
    }

    public Integer getMembers() {
        return members;
    }

    public GroupViewModel setMembers(Integer members) {
        this.members = members;
        return this;
    }

    public Set<String> getMembersName() {
        return membersName;
    }

    public GroupViewModel setMembersName(Set<String> membersName) {
        this.membersName = membersName;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public GroupViewModel setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public LocalDate getCreated() {
        return created;
    }

    public GroupViewModel setCreated(LocalDate created) {
        this.created = created;
        return this;
    }

    public boolean isCanJoin() {
        return canJoin;
    }

    public GroupViewModel setCanJoin(boolean canJoin) {
        this.canJoin = canJoin;
        return this;
    }

    public EventViewModel getEventViewModel() {
        return eventViewModel;
    }

    public GroupViewModel setEventViewModel(EventViewModel eventViewModel) {
        this.eventViewModel = eventViewModel;
        return this;
    }
}
