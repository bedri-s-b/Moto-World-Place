package com.example.motoworldplace.repository;

import com.example.motoworldplace.model.entity.CityEntity;
import com.example.motoworldplace.model.entity.enums.CityEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityEntity,Long> {

    Optional<CityEntity> findByName(CityEnum name);
}
