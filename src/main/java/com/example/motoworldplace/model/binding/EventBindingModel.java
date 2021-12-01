package com.example.motoworldplace.model.binding;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class EventBindingModel {

    private String title;
    private String description;
   // private String user;
    private String started;
    private String creator;
    private String group;
    private LocalDateTime presentOrFuture;

    @Size(min = 2)
    @NotNull
    public String getTitle() {
        return title;
    }

    public EventBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }

    @Size(min = 5)
    @NotNull
    public String getDescription() {
        return description;
    }

    public EventBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }

   @NotNull
    public String getStarted() {
        return started;
    }

    public EventBindingModel setStarted(String started) {
        this.started = started;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public EventBindingModel setCreator(String creator) {
        this.creator = creator;
        return this;
    }


    public String getGroup() {
        return group;
    }

    public EventBindingModel setGroup(String group) {
        this.group = group;
        return this;
    }


    public LocalDateTime getPresentOrFuture() {
        return presentOrFuture;
    }

    public EventBindingModel setPresentOrFuture(LocalDateTime presentOrFuture) {
        this.presentOrFuture = presentOrFuture;
        return this;
    }
}
