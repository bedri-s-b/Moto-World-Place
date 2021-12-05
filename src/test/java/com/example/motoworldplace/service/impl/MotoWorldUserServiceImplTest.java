package com.example.motoworldplace.service.impl;

import com.example.motoworldplace.model.entity.CityEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import com.example.motoworldplace.model.entity.enums.CityEnum;
import com.example.motoworldplace.model.entity.enums.RoleEnum;
import com.example.motoworldplace.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.internal.util.Assert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MotoWorldUserServiceImplTest {

    private UserEntity testUser;
    private CityEntity testCity;

    private MotoWorldUserServiceImpl serviceToTest;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void init(){
        serviceToTest = new MotoWorldUserServiceImpl(userRepository);

        testCity = new CityEntity().setName(CityEnum.СОФИЯ);

        testUser = new UserEntity()
                .setUsername("Test")
                .setEmail("test@abv.bg")
                .setPassword("testova")
                .setRole(RoleEnum.ADMIN)
                .setCity(testCity)
                .setGroupEnum(RoleEnum.GROUP_ADMIN)
                .setFullName("Test Testov");
    }

    @Test
    void userNotFound(){
        assertThrows(UsernameNotFoundException.class,
                () -> {
                    serviceToTest.loadUserByUsername("invalid_username");
                });

    }

    @Test
    void userFound(){
        Mockito.when(userRepository.findByUsername(testUser.getUsername()))
                .thenReturn(Optional.of(testUser));

        UserDetails actual = serviceToTest.loadUserByUsername(testUser.getUsername());

        String expectedRoles = "ROLE_ADMIN, ROLE_GROUP_ADMIN";
        String actualRoles = actual.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", "));

        assertEquals(actual.getUsername(),testUser.getUsername());

        assertEquals(expectedRoles,actualRoles);
    }
}