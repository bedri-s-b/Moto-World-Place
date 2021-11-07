package com.example.motoworldplace.config;

import com.example.motoworldplace.model.entiy.CityEntity;
import com.example.motoworldplace.model.entiy.UserEntity;
import com.example.motoworldplace.model.entiy.enums.CityEnum;
import com.example.motoworldplace.model.service.UserServiceModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class ApplicationConfig {

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
}
