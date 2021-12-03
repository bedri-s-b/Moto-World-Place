package com.example.motoworldplace.model.view;

import com.example.motoworldplace.model.service.UserEventServiceModel;

import java.util.HashSet;
import java.util.Set;

public class EventViewModel {

    private Long id;
    private String title;
    private String description;
    private String started;
    private String creator;
    private String group;
    private Set<UserEventServiceModel> membersCome = new HashSet<>();
    private boolean isMemberEvent;
    private boolean canDelete;

    public EventViewModel() {
    }


    public EventViewModel(Long id, String title, String description, String stared, String creator, String group) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.started = stared;
        this.creator = creator;
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public EventViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public EventViewModel setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EventViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getStarted() {
        return started;
    }

    public EventViewModel setStarted(String started) {
        this.started = started;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public EventViewModel setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public EventViewModel setGroup(String group) {
        this.group = group;
        return this;
    }

    public Set<UserEventServiceModel> getMembersCome() {
        return membersCome;
    }

    public EventViewModel setMembersCome(Set<UserEventServiceModel> membersCome) {
        this.membersCome = membersCome;
        return this;
    }

    public boolean isMemberEvent() {
        return isMemberEvent;
    }

    public EventViewModel setMemberEvent(boolean memberEvent) {
        isMemberEvent = memberEvent;
        return this;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public EventViewModel setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
        return this;
    }
}
