package com.example.motoworldplace.repository;

import com.example.motoworldplace.model.entiy.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity,Long> {

    PictureEntity findByUrl(String url);
}
