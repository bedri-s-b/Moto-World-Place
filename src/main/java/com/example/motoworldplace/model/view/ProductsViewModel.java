package com.example.motoworldplace.model.view;

import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import com.example.motoworldplace.model.entity.enums.TypeEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ProductsViewModel {

    private Long id;
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
    private Set<String> pictures = new HashSet<>();
    private String created;

    public String getBrand() {
        return brand;
    }

    public ProductsViewModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getModel() {
        return model;
    }

    public ProductsViewModel setModel(String model) {
        this.model = model;
        return this;
    }

    public TypeEnum getType() {
        return type;
    }

    public ProductsViewModel setType(TypeEnum type) {
        this.type = type;
        return this;
    }

    public Integer getPowerHp() {
        return powerHp;
    }

    public ProductsViewModel setPowerHp(Integer powerHp) {
        this.powerHp = powerHp;
        return this;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public ProductsViewModel setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductsViewModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Integer getYear() {
        return year;
    }

    public ProductsViewModel setYear(Integer year) {
        this.year = year;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ProductsViewModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }


    public String getDescription() {
        return description;
    }

    public ProductsViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<String> getPictures() {
        return pictures;
    }

    public ProductsViewModel setPictures(Set<String> pictures) {
        this.pictures = pictures;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ProductsViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSeller() {
        return seller;
    }

    public ProductsViewModel setSeller(String seller) {
        this.seller = seller;
        return this;
    }

    public String getCreated() {
        return created;
    }

    public ProductsViewModel setCreated(String created) {
        this.created = created;
        return this;
    }
}
