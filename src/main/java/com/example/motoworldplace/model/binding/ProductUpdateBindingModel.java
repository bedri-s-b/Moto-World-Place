package com.example.motoworldplace.model.binding;

import com.example.motoworldplace.model.entity.enums.TypeEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ProductUpdateBindingModel {
    private Long id;
    private String brand;
    private String model;
    private TypeEnum type;
    private Integer powerHp;
    private Integer kilometers;
    private BigDecimal price;
    private Integer year;
    private String phoneNumber;
//    private String seller;
    private String description;


    public Long getId() {
        return id;
    }

    public ProductUpdateBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    @NotNull
    public String getBrand() {
        return brand;
    }

    public ProductUpdateBindingModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    @NotNull
    public String getModel() {
        return model;
    }

    public ProductUpdateBindingModel setModel(String model) {
        this.model = model;
        return this;
    }

    @NotNull
    public TypeEnum getType() {
        return type;
    }

    public ProductUpdateBindingModel setType(TypeEnum type) {
        this.type = type;
        return this;
    }

    @NotNull
    @Positive
    public Integer getPowerHp() {
        return powerHp;
    }

    public ProductUpdateBindingModel setPowerHp(Integer powerHp) {
        this.powerHp = powerHp;
        return this;
    }

    @NotNull
    @Positive
    public Integer getKilometers() {
        return kilometers;
    }

    public ProductUpdateBindingModel setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
        return this;
    }

    @NotNull
    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public ProductUpdateBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @NotNull
    @Positive
    public Integer getYear() {
        return year;
    }

    public ProductUpdateBindingModel setYear(Integer year) {
        this.year = year;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ProductUpdateBindingModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

//    public String getSeller() {
//        return seller;
//    }
//
//    public ProductUpdateBindingModel setSeller(String seller) {
//        this.seller = seller;
//        return this;
//    }

    @NotNull
    @Size(min = 5)
    public String getDescription() {
        return description;
    }

    public ProductUpdateBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
