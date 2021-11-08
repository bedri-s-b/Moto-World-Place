package com.example.motoworldplace.model.binding;

import com.example.motoworldplace.model.entity.enums.CityEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {

    private String username;
    private String fullName;
    private String email;
    private Integer age;
    private CityEnum city;
    private String password;
    private String confirmPassword;

    public UserRegisterBindingModel() {
    }


    @NotNull
    @Size(min = 5,max = 20)
    public String getUsername() {
        return username;
    }

    public UserRegisterBindingModel setUsername(String username) {
        this.username = username;
        return this;
    }

    @NotNull
    @Size(min = 5,max = 20)
    public String getFullName() {
        return fullName;
    }

    public UserRegisterBindingModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    @Email
    @NotNull
    public String getEmail() {
        return email;
    }

    public UserRegisterBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @NotNull
    @Min(16)
    public Integer getAge() {
        return age;
    }

    public UserRegisterBindingModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    @NotNull
    public CityEnum getCity() {
        return city;
    }

    public UserRegisterBindingModel setCity(CityEnum city) {
        this.city = city;
        return this;
    }

    @NotNull
    @Size(min = 5)
    public String getPassword() {
        return password;
    }

    public UserRegisterBindingModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @Size(min = 5)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegisterBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
