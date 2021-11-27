package com.example.motoworldplace.model.entity;

import com.example.motoworldplace.model.entity.enums.TypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
public class ProductEntity extends BaseEntity{

    private String brand;
    private String model;
    private TypeEnum type;
    private Integer powerHp;
    private Integer kilometers;
    private BigDecimal price;
    private Integer year;
    private String phoneNumber;
    private UserEntity seller;
    private String description;
    private Set<PictureEntity> pictures = new HashSet<>();
    private LocalDateTime created;

    public String getDescription() {
        return description;
    }

    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }


    @Enumerated(EnumType.STRING)
    public TypeEnum getType() {
        return type;
    }

    public ProductEntity setType(TypeEnum type) {
        this.type = type;
        return this;
    }

    @ManyToOne
    public UserEntity getSeller() {
        return seller;
    }

    public ProductEntity setSeller(UserEntity seller) {
        this.seller = seller;
        return this;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public ProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }


    @OneToMany(mappedBy = "product")
    public Set<PictureEntity> getPictures() {
        return pictures;
    }

    public ProductEntity setPictures(Set<PictureEntity> picturesUrl) {
        this.pictures = picturesUrl;
        return this;
    }

   @Column(nullable = false)
    public Integer getKilometers() {
        return kilometers;
    }

    public ProductEntity setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
        return this;
    }

    @Column(nullable = false)
    public Integer getYear() {
        return year;
    }

    public ProductEntity setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Integer getPowerHp() {
        return powerHp;
    }

    public ProductEntity setPowerHp(Integer powerHp) {
        this.powerHp = powerHp;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ProductEntity setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Column(nullable = false)
    public String getModel() {
        return model;
    }

    public ProductEntity setModel(String model) {
        this.model = model;
        return this;
    }

    @Column(nullable = false)
    public String getBrand() {
        return brand;
    }

    public ProductEntity setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public ProductEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    @PrePersist
    private void setCreated(){
        setCreated(LocalDateTime.now());
    }
}
