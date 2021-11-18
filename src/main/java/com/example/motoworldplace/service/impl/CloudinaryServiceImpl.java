package com.example.motoworldplace.service.impl;

import com.cloudinary.Cloudinary;
import com.example.motoworldplace.service.cluodinary.CloudinaryImg;
import com.example.motoworldplace.service.cluodinary.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private static final String PUBLIC_ID = "public_id";

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public CloudinaryImg upload(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile(TEMP_FILE, file.getOriginalFilename());
        file.transferTo(tempFile);

        CloudinaryImg result = null;
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> uploadResult = cloudinary.
                    uploader().
                    upload(tempFile, Map.of());

            String url = uploadResult.getOrDefault(URL, "https://img.freepik.com/free-vector/404-error-with-character-error-design-template-website_114341-24.jpg?size=626&ext=jpg");
            String publicId = uploadResult.getOrDefault(PUBLIC_ID, "https://img.freepik.com/free-vector/404-error-with-character-error-design-template-website_114341-24.jpg?size=626&ext=jpg");

            result = new CloudinaryImg().setUrl(url).
                    setPublicId(publicId);
        } finally {
            tempFile.delete();
        }
        return result;
    }

    @Override
    public boolean delete(String publicId) {
        try {
            this.cloudinary.uploader().destroy(publicId, Map.of());
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
