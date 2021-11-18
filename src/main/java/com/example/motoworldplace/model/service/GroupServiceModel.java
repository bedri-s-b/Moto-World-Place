package com.example.motoworldplace.model.service;

import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.model.entity.UserEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class GroupServiceModel {

    private Long id;
    private String name;
    private UserEntity admin;
    private Set<UserEntity> members = new HashSet<>();
    private PictureEntity picture;
    private Set<PictureEntity> pictures = new HashSet<>();
    private LocalDate created;

    public Long getId() {
        return id;
    }

    public GroupServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public GroupServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public UserEntity getAdmin() {
        return admin;
    }

    public GroupServiceModel setAdmin(UserEntity admin) {
        this.admin = admin;
        return this;
    }

    public Set<UserEntity> getMembers() {
        return members;
    }

    public GroupServiceModel setMembers(Set<UserEntity> members) {
        this.members = members;
        return this;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public GroupServiceModel setPicture(PictureEntity picture) {
        this.picture = picture;
        return this;
    }

    public Set<PictureEntity> getPictures() {
        return pictures;
    }

    public GroupServiceModel setPictures(Set<PictureEntity> pictures) {
        this.pictures = pictures;
        return this;
    }

    public LocalDate getCreated() {
        return created;
    }

    public GroupServiceModel setCreated(LocalDate created) {
        this.created = created;
        return this;
    }
}
