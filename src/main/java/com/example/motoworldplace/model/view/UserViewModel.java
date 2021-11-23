package com.example.motoworldplace.model.view;

import com.example.motoworldplace.model.entity.CityEntity;
import com.example.motoworldplace.model.entity.GroupEntity;
import com.example.motoworldplace.model.entity.MessageEntity;
import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.model.entity.enums.CityEnum;
import com.example.motoworldplace.model.entity.enums.RoleEnum;

import java.util.HashSet;
import java.util.Set;

public class UserViewModel {

    private Long id;
    private String username;
    private String fullName;
    private Integer age;
    private CityEnum city;
    private String picture;
    private Set<String> groups = new HashSet<>();
    private RoleEnum role;

    public Long getId() {
        return id;
    }

    public UserViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserViewModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public UserViewModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public UserViewModel setAge(Integer age) {
        this.age = age;
        return this;
    }

    public CityEnum getCity() {
        return city;
    }

    public UserViewModel setCity(CityEnum city) {
        this.city = city;
        return this;
    }

    public String getPicture() {
        return picture;
    }

    public UserViewModel setPicture(String picture) {
        this.picture = picture;
        return this;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public UserViewModel setGroups(Set<String> groups) {
        this.groups = groups;
        return this;
    }

    public RoleEnum getRole() {
        return role;
    }

    public UserViewModel setRole(RoleEnum role) {
        this.role = role;
        return this;
    }
//    public String getUsername() {
//        return username;
//    }
//
//    public UserViewModel setUsername(String username) {
//        this.username = username;
//        return this;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public UserViewModel setFullName(String fullName) {
//        this.fullName = fullName;
//        return this;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public UserViewModel setAge(Integer age) {
//        this.age = age;
//        return this;
//    }
//
//    public CityEnum getCity() {
//        return city;
//    }
//
//    public UserViewModel setCity(CityEnum city) {
//        this.city = city;
//        return this;
//    }
//
//    public PictureEntity getPicture() {
//        return picture;
//    }
//
//    public UserViewModel setPicture(PictureEntity picture) {
//        this.picture = picture;
//        return this;
//    }
//
//    public Set<String> getGroups() {
//        return groups;
//    }
//
//    public UserViewModel setGroups(Set<String> groups) {
//        this.groups = groups;
//        return this;
//    }
//
//    public RoleEnum getRole() {
//        return role;
//    }
//
//    public UserViewModel setRole(RoleEnum role) {
//        this.role = role;
//        return this;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public UserViewModel setId(Long id) {
//        this.id = id;
//        return this;
//    }
}
