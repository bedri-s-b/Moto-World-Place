package com.example.motoworldplace.model.entiy;

import com.example.motoworldplace.model.entiy.enums.CityEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "cities")
public class CityEntity extends BaseEntity{
    private CityEnum name;


    @Enumerated(EnumType.STRING)
    public CityEnum getName() {
        return name;
    }

    public CityEntity setName(CityEnum name) {
        this.name = name;
        return this;
    }
}
