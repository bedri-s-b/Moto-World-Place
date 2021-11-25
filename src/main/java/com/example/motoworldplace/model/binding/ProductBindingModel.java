package com.example.motoworldplace.model.binding;

import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import com.example.motoworldplace.model.entity.enums.TypeEnum;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ProductBindingModel {

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
    private MultipartFile picture1;
    private MultipartFile picture2;
    private MultipartFile picture3;
    private MultipartFile picture4;
    private Set<MultipartFile> pictures = new HashSet<>();

    @NotNull
    public String getBrand() {
        return brand;
    }

    public ProductBindingModel setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    @NotNull
    public String getModel() {
        return model;
    }

    public ProductBindingModel setModel(String model) {
        this.model = model;
        return this;
    }

    @NotNull
    public TypeEnum getType() {
        return type;
    }

    public ProductBindingModel setType(TypeEnum type) {
        this.type = type;
        return this;
    }

    @NotNull
    @Positive
    public Integer getPowerHp() {
        return powerHp;
    }

    public ProductBindingModel setPowerHp(Integer powerHp) {
        this.powerHp = powerHp;
        return this;
    }

    @NotNull
    @Positive
    public Integer getKilometers() {
        return kilometers;
    }

    public ProductBindingModel setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
        return this;
    }

    @NotNull
    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public ProductBindingModel setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    @NotNull
    @Positive
    public Integer getYear() {
        return year;
    }

    public ProductBindingModel setYear(Integer year) {
        this.year = year;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ProductBindingModel setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getSeller() {
        return seller;
    }

    public ProductBindingModel setSeller(String seller) {
        this.seller = seller;
        return this;
    }

    @NotNull
    @Size(min = 5)
    public String getDescription() {
        return description;
    }

    public ProductBindingModel setDescription(String description) {
        this.description = description;
        return this;
    }


    public MultipartFile getPicture1() {
        return picture1;
    }

    public ProductBindingModel setPicture1(MultipartFile picture1) {
        this.picture1 = picture1;
        return this;
    }

    public MultipartFile getPicture2() {
        return picture2;
    }

    public ProductBindingModel setPicture2(MultipartFile picture2) {
        this.picture2 = picture2;
        return this;
    }

    public MultipartFile getPicture3() {
        return picture3;
    }

    public ProductBindingModel setPicture3(MultipartFile picture3) {
        this.picture3 = picture3;
        return this;
    }

    public MultipartFile getPicture4() {
        return picture4;
    }

    public ProductBindingModel setPicture4(MultipartFile picture4) {
        this.picture4 = picture4;
        return this;
    }


    public Set<MultipartFile> getPictures() {
        return pictures;
    }

    public ProductBindingModel setPictures(Set<MultipartFile> pictures) {
        this.pictures = pictures;
        return this;
    }

    public ProductBindingModel loadPictures(){
        if (!picture1.getOriginalFilename().equals("")){
            pictures.add(picture1);
        }
        if (!picture2.getOriginalFilename().equals("")){
            pictures.add(picture2);
        }
        if (!picture3.getOriginalFilename().equals("")){
            pictures.add(picture3);
        }
        if (!picture4.getOriginalFilename().equals("")){
            pictures.add(picture4);
        }
        return this;
    }
}
