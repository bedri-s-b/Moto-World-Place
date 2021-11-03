package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.entiy.PictureEntity;
import com.example.motoworldplace.repository.PictureRepository;
import com.example.motoworldplace.service.PictureService;
import org.springframework.stereotype.Service;

import static com.example.motoworldplace.constans.ConstantsUrl.DEFAULT_URL_PIC;

@Service
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }


    @Override
    public void initPic() {
        if (pictureRepository.count() != 0){
            return;
        }
        PictureEntity picture = new PictureEntity().setUrl(DEFAULT_URL_PIC);
        pictureRepository.save(picture);
    }

    @Override
    public PictureEntity getDefaultPic() {
        return pictureRepository.findByUrl(DEFAULT_URL_PIC);
    }
}
