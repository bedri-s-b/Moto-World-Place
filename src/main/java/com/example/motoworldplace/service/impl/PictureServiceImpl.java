package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.repository.PictureRepository;
import com.example.motoworldplace.service.PictureService;
import com.example.motoworldplace.service.cluodinary.CloudinaryImg;
import com.example.motoworldplace.service.cluodinary.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.motoworldplace.constans.ConstantsUrl.DEFAULT_URL_PIC_GROUP;
import static com.example.motoworldplace.constans.ConstantsUrl.DEFAULT_URL_PIC_USER;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final CloudinaryService cloudinaryService;


    public PictureServiceImpl(PictureRepository pictureRepository, CloudinaryService cloudinaryService) {
        this.pictureRepository = pictureRepository;
        this.cloudinaryService = cloudinaryService;

    }


    @Override
    public void initPic() {
        if (pictureRepository.count() != 0){
            return;
        }
        PictureEntity picture = new PictureEntity().setUrl(DEFAULT_URL_PIC_USER).setPublicId("default");
        pictureRepository.saveAndFlush(picture);
        PictureEntity picture2 = new PictureEntity().setUrl(DEFAULT_URL_PIC_GROUP).setPublicId("default");
        pictureRepository.save(picture2);
    }


    @Override
    public String createNewPicture(MultipartFile picture, Long id) throws IOException {
        CloudinaryImg uploaded = this.cloudinaryService.upload(picture);

        PictureEntity pictureEntity = new PictureEntity().setUrl(uploaded.getUrl())
                .setPublicId(uploaded
                        .getPublicId());

        return pictureRepository.save(pictureEntity).getPublicId();
    }

    @Override
    public boolean existPicture(String picUrl) {
        return false;
    }

    @Override
    public PictureEntity findByPublicId(String id) {
        return pictureRepository.findByPublicId(id);
    }

    @Override
    public boolean deleteByPublicId(String publicId) {
        return pictureRepository.deleteByPublicId(publicId);
    }

    @Override
    public void delete(PictureEntity picture) {
        pictureRepository.delete(picture);
    }

    @Override
    public void savePicture(PictureEntity picture) {
        pictureRepository.saveAndFlush(picture);
    }


    @Override
    public PictureEntity getDefaultPic() {
        return pictureRepository.findByUrl(DEFAULT_URL_PIC_USER);
    }

    @Override
    public PictureEntity getDefaultGroupPic() {
        return pictureRepository.findByUrl(DEFAULT_URL_PIC_GROUP);
    }
}
