package com.example.motoworldplace.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GroupAddBindingModel {

    private String name;
    private Long adminId;
    private MultipartFile picture;

    @NotNull
    @Size(min = 5, max = 20)
    public String getName() {
        return name;
    }

    public GroupAddBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public Long getAdminId() {
        return adminId;
    }

    public GroupAddBindingModel setAdminId(Long adminId) {
        this.adminId = adminId;
        return this;
    }

    @NotNull
    public MultipartFile getPicture() {
        return picture;
    }

    public GroupAddBindingModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }


}
