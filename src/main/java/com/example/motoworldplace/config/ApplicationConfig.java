package com.example.motoworldplace.config;

import com.cloudinary.Cloudinary;
import com.example.motoworldplace.model.entity.CityEntity;
import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.model.entity.UserEntity;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        Converter<UserEntity, String> converter1 = new Converter<UserEntity, String>() {
            @Override
            public String convert(MappingContext<UserEntity, String> mappingContext) {
                UserEntity source = mappingContext.getSource();
                return source.getUsername();
            }
        } ;

        Converter<PictureEntity ,String> converter2 = new Converter<PictureEntity, String>() {
            @Override
            public String convert(MappingContext<PictureEntity, String> mappingContext) {
                return mappingContext.getSource().getUrl();
            }
        };

        Converter<LocalDateTime,String> converter3 = new Converter<LocalDateTime, String>() {
            @Override
            public String convert(MappingContext<LocalDateTime, String> mappingContext) {
                LocalDateTime source = mappingContext.getSource();
                return source.format(DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm"));
            }
        };

        modelMapper.addConverter(converter);
        modelMapper.addConverter(converter1);
        modelMapper.addConverter(converter2);
        modelMapper.addConverter(converter3);
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
