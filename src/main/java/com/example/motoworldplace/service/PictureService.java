package com.example.motoworldplace.service;

import com.example.motoworldplace.model.entity.PictureEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface PictureService {

    PictureEntity getDefaultPic();

    void initPic();

    String createNewPicture(MultipartFile picture, Long idCurrentUser) throws IOException;

    boolean existPicture(String picUrl);

    PictureEntity findByPublicId(String id);

    boolean deleteByPublicId(String publicId);

    void delete(PictureEntity picture);
}
