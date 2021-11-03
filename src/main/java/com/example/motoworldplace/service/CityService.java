package com.example.motoworldplace.service;

import com.example.motoworldplace.model.entiy.CityEntity;
import com.example.motoworldplace.model.entiy.enums.CityEnum;

public interface CityService {
    void initCities();

    CityEntity findByName(CityEnum name);
}
