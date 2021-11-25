package com.example.motoworldplace.model.service;

import com.example.motoworldplace.model.entity.enums.TypeEnum;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ProductServiceModel {

    private String brand;
    private String model;
    private TypeEnum type;
    private Integer powerHp;
    private Integer kilometers;
    private BigDecimal price;
    private Integer year;
    private String phoneNumber;
    private String seller;
    private String description;
    private Set<MultipartFile> pictures = new HashSet<>();

    public String getBrand() {
        return brand;
    }

    public ProductServiceModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public ProductServiceModel setModel(String model) {
        this.model = model;
        return this;
    }

    public TypeEnum getType() {
        return type;
    }

    public ProductServiceModel setType(TypeEnum type) {
        this.type = type;
        return this;
    }

    public Integer getPowerHp() {
        return powerHp;
    }

    public ProductServiceModel setPowerHp(Integer powerHp) {
        this.powerHp = powerHp;
        return this;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public ProductServiceModel setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductServiceModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public ProductServiceModel setYear(Integer year) {
        this.year = year;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ProductServiceModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getSeller() {
        return seller;
    }

    public ProductServiceModel setSeller(String seller) {
        this.seller = seller;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<MultipartFile> getPictures() {
        return pictures;
    }

    public ProductServiceModel setPictures(Set<MultipartFile> pictures) {
        this.pictures = pictures;
        return this;
    }
}
