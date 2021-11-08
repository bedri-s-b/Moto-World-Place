package com.example.motoworldplace.repository;

import com.example.motoworldplace.model.entity.PictureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Repository
public interface PictureRepository extends JpaRepository<PictureEntity, Long> {

    PictureEntity findByUrl(String url);

    PictureEntity findByPublicId(String publicId);

    @Transactional
    boolean deleteByPublicId(String publicId);

}
