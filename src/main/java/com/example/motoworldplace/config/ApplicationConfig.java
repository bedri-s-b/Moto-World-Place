package com.example.motoworldplace.config;

import com.cloudinary.Cloudinary;
import com.example.motoworldplace.model.entity.CityEntity;
import com.example.motoworldplace.model.entity.GroupEntity;
import com.example.motoworldplace.model.entity.PictureEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import com.example.motoworldplace.model.entity.enums.CityEnum;
import com.example.motoworldplace.model.view.GroupViewModel;
import com.example.motoworldplace.model.view.UserViewModel;
import com.example.motoworldplace.repository.PictureRepository;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfig {
    private final CloudinaryConfig cloudinaryConfig;
    private final PictureRepository pictureRepository;

    public ApplicationConfig(CloudinaryConfig cloudinaryConfig, PictureRepository pictureRepository) {
        this.cloudinaryConfig = cloudinaryConfig;
        this.pictureRepository = pictureRepository;
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

        Converter<Set<PictureEntity>,Set<String>> converter4 = new Converter<Set<PictureEntity>, Set<String>>() {
            @Override
            public Set<String> convert(MappingContext<Set<PictureEntity>, Set<String>> mappingContext) {
                Set<PictureEntity> source = mappingContext.getSource();
               return source.stream().map(PictureEntity::getUrl).collect(Collectors.toSet());
            }
        };

       Converter<Set<GroupEntity>,Set<String>> converter5 = new Converter<Set<GroupEntity>, Set<String>>() {
           @Override
           public Set<String> convert(MappingContext<Set<GroupEntity>, Set<String>> mappingContext) {
               Set<GroupEntity> source = mappingContext.getSource();
               return source.stream().map(GroupEntity::getName).collect(Collectors.toSet());
           }
       };




        modelMapper.addConverter(converter);
        modelMapper.addConverter(converter1);
        modelMapper.addConverter(converter2);
        modelMapper.addConverter(converter3);
        modelMapper.addConverter(converter4);
        modelMapper.addConverter(converter5);
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
