package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.entity.CityEntity;
import com.example.motoworldplace.model.entity.enums.CityEnum;
import com.example.motoworldplace.repository.CityRepository;
import com.example.motoworldplace.service.CityService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public void initCities() {
        if (cityRepository.count() != 0){
            return;
        }
        CityEnum[]  cityEnums = CityEnum.values();
        Arrays.stream(cityEnums)
                .forEach(cityEnum -> {
                    CityEntity city = new CityEntity();
                    cityRepository.save(city.setName(cityEnum));
                });
    }

    @Override
    public CityEntity findByName(CityEnum name) {
        return cityRepository.findByName(name).orElse(null);
    }
}
