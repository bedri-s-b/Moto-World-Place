package com.example.motoworldplace.web;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.example.motoworldplace.model.entity.EventEntity;
import com.example.motoworldplace.model.entity.ProductEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import com.example.motoworldplace.model.entity.enums.RoleEnum;
import com.example.motoworldplace.model.entity.enums.TypeEnum;
import com.example.motoworldplace.repository.EventRepository;
import com.example.motoworldplace.repository.GroupRepository;
import com.example.motoworldplace.repository.ProductRepository;
import com.example.motoworldplace.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@WithMockUser(value = "test", authorities = "ROLE_ADMIN")
@SpringBootTest
@AutoConfigureMockMvc
class AdminRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EventRepository eventRepository;


    @Autowired
    private GroupRepository groupRepository;


    private UserEntity testUser;

    @AfterEach
    void tearDown() {
       productRepository.deleteAll();
       eventRepository.deleteAll();
       userRepository.deleteAll();
    }

    @BeforeEach
    void setUp() {
        testUser = new UserEntity().setRole(RoleEnum.ADMIN)
                .setGroupEnum(RoleEnum.GROUP_ADMIN).setUsername("test")
                .setFullName("TestTestov").setAge(20).setPassword("test");

        userRepository.save(testUser);


    }

    @Test
    void testGetProducts() throws Exception {
        initProduct();

        mockMvc.perform(get("/admin/all/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$.[0].brand",is("Honda")))
                .andExpect(jsonPath("$.[0].model",is("Hornet")))
                .andExpect(jsonPath("$.[0].powerHp",is(101)));
    }

    private ProductEntity testProduct;

    private void initProduct() {
        testProduct = new ProductEntity()
                .setModel("Hornet")
                .setBrand("Honda")
                .setType(TypeEnum.CLASSIC)
                .setPowerHp(101)
                .setKilometers(10000)
                .setPrice(BigDecimal.TEN)
                .setYear(2008)
                .setDescription("Very good")
                .setSeller(userRepository.findByUsername("test").get());

        productRepository.save(testProduct);
    }

//    @Test
//    void getAllEvents() throws Exception {
//        intEvent();
//        mockMvc.perform(get("/admin/all/events"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$",hasSize(1)))
//                .andExpect(jsonPath("$.[0].title",is("testEvent")))
//                .andExpect(jsonPath("$.[0].description",is("test description")));
//
//
//
//    }
//
//    private EventEntity testEvent;
//
//    private void intEvent() {
//        testEvent = new EventEntity().setTitle("testEvent")
//                .setDescription("test description")
//                .setStared(LocalDateTime.now())
//                .setGroup(groupRepository.findByName("FREE_RAIDER"))
//                .setCreator(userRepository.getById(1L))
//                .setMembersCome(Set.of(userRepository.getById(1L)));
//        eventRepository.save(testEvent);
//    }

}