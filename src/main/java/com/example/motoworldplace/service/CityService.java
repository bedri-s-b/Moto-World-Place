package com.example.motoworldplace.service;

import com.example.motoworldplace.model.entity.CityEntity;
import com.example.motoworldplace.model.entity.enums.CityEnum;

public interface CityService {
    void initCities();

    CityEntity findByName(CityEnum name);
}
