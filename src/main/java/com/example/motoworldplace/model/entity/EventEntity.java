package com.example.motoworldplace.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
public class EventEntity extends BaseEntity{

    private String title;
    private String description;
    private LocalDateTime stared;
    private UserEntity creator;
    private GroupEntity group;
    private Set<UserEntity> membersCome = new HashSet<>();

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    public EventEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    @Column(nullable = false)
    @Lob
    public String getDescription() {
        return description;
    }

    public EventEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column(nullable = false)
    public LocalDateTime getStared() {
        return stared;
    }

    public EventEntity setStared(LocalDateTime stared) {
        this.stared = stared;
        return this;
    }

    @ManyToOne
    public UserEntity getCreator() {
        return creator;
    }

    public EventEntity setCreator(UserEntity creator) {
        this.creator = creator;
        return this;
    }

    @ManyToOne
    public GroupEntity getGroup() {
        return group;
    }

    public EventEntity setGroup(GroupEntity group) {
        this.group = group;
        return this;
    }


    @ManyToMany
    public Set<UserEntity> getMembersCome() {
        return membersCome;
    }

    public EventEntity setMembersCome(Set<UserEntity> membersCome) {
        this.membersCome = membersCome;
        return this;
    }
}
