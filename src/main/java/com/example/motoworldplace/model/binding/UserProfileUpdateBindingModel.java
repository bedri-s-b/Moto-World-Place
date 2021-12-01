package com.example.motoworldplace.model.binding;

import com.example.motoworldplace.model.entity.enums.CityEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;

public class UserProfileUpdateBindingModel {

    private Long id;
    private MultipartFile picture;
    private String fullName;
    private CityEnum city;
    private String username;
    private String email;

    public UserProfileUpdateBindingModel() {
    }


    public Long getId() {
        return id;
    }

    public UserProfileUpdateBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public UserProfileUpdateBindingModel setPicture(MultipartFile picture) {
        this.picture = picture;
        return this;
    }


    @NotNull
    @Size(min = 5,max = 20)
    public String getFullName() {
        return fullName;
    }

    public UserProfileUpdateBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public CityEnum getCity() {
        return city;
    }

    public UserProfileUpdateBindingModel setCity(CityEnum city) {
        this.city = city;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserProfileUpdateBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @Email
    @NotNull
    public String getEmail() {
        return email;
    }

    public UserProfileUpdateBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }
}
