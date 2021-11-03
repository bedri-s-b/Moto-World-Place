package com.example.motoworldplace.model.entiy;

import com.example.motoworldplace.model.entiy.enums.TypeEnum;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "offers")
public class OfferEntity extends BaseEntity{

    private String description;
    private TypeEnum type;
    private BigDecimal price;
    private Set<PictureEntity> picturesUrl = new HashSet<>();
    private UserEntity seller;

    public String getDescription() {
        return description;
    }

    public OfferEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public TypeEnum getType() {
        return type;
    }

    public OfferEntity setType(TypeEnum type) {
        this.type = type;
        return this;
    }

    @ManyToOne
    public UserEntity getSeller() {
        return seller;
    }

    public OfferEntity setSeller(UserEntity seller) {
        this.seller = seller;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OfferEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }


    @OneToMany()
    public Set<PictureEntity> getPicturesUrl() {
        return picturesUrl;
    }

    public OfferEntity setPicturesUrl(Set<PictureEntity> picturesUrl) {
        this.picturesUrl = picturesUrl;
        return this;
    }
}
