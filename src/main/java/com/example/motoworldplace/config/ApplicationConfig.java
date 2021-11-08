package com.example.motoworldplace.config;

import com.cloudinary.Cloudinary;
import com.example.motoworldplace.model.entity.CityEntity;
import com.example.motoworldplace.model.entity.enums.CityEnum;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.security.Principal;
import java.util.Map;

@Configuration
public class ApplicationConfig {
    private final CloudinaryConfig cloudinaryConfig;

    public ApplicationConfig(CloudinaryConfig cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        Converter<CityEntity, CityEnum> converter = new Converter<CityEntity, CityEnum>() {
            @Override
            public CityEnum convert(MappingContext<CityEntity, CityEnum> mappingContext) {
                CityEntity source = mappingContext.getSource();
                return source.getName();
            }
        };
        modelMapper.addConverter(converter);
        return modelMapper;
    };

    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new Pbkdf2PasswordEncoder();
    }

    @Bean
    public Cloudinary cloudinary(){
        return new Cloudinary(
                Map.of(
                      "cloud_name",cloudinaryConfig.getCloudName(),
                      "api_key",cloudinaryConfig.getApiKey(),
                      "api_secret",cloudinaryConfig.getApiSecret()
                )
        );
    }

}
