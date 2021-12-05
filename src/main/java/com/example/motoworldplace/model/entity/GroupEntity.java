package com.example.motoworldplace.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "moto_groups")
public class GroupEntity extends BaseEntity {

    private String name;
    private UserEntity admin;
    private Set<UserEntity> members = new HashSet<>();
    private PictureEntity picture;
    private LocalDate created;

    public GroupEntity() {
    }


    @ManyToMany(mappedBy = "groups",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    public Set<UserEntity> getMembers() {
        return members;
    }

    public GroupEntity setMembers(Set<UserEntity> members) {
        this.members = members;
        return this;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public GroupEntity setName(String name) {
        this.name = name;
        return this;
    }

    @ManyToOne
    public UserEntity getAdmin() {
        return admin;
    }

    public GroupEntity setAdmin(UserEntity admin) {
        this.admin = admin;
        return this;
    }


    @ManyToOne
    public PictureEntity getPicture() {
        return picture;
    }

    public GroupEntity setPicture(PictureEntity picture) {
        this.picture = picture;
        return this;
    }

    public LocalDate getCreated() {
        return created;
    }

    public GroupEntity setCreated(LocalDate created) {
        this.created = created;
        return this;
    }

    @PrePersist
    public void beforeCreate() {
        setCreated(LocalDate.now());
    }
}
