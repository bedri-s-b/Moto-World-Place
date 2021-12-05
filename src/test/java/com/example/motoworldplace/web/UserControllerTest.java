package com.example.motoworldplace.web;

import com.example.motoworldplace.model.entity.GroupEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import com.example.motoworldplace.model.entity.enums.RoleEnum;
import com.example.motoworldplace.repository.GroupRepository;
import com.example.motoworldplace.repository.MessageRepository;
import com.example.motoworldplace.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private MessageRepository messageRepository;

    @AfterEach
    void tearDown() {
        messageRepository.deleteAll();
        userRepository.deleteAll();
        groupRepository.deleteAll();
    }

    private GroupEntity testGroup;
    private UserEntity testUser;

    @BeforeEach
    void init() {

        testUser = new UserEntity().setRole(RoleEnum.ADMIN)
                .setGroupEnum(RoleEnum.GROUP_ADMIN).setUsername("test")
                .setFullName("TestTestov").setAge(20).setPassword("test");

        UserEntity save = userRepository.save(testUser);

        testGroup = new GroupEntity().setName("free").setAdmin(save);
        groupRepository.save(testGroup);
    }


    @Test
    void testOpenRegisterForm() throws Exception {
        mockMvc.
                perform(get("/users/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    private static final String TEST_USER_NAME = "TEST1";

    @Test
    void testRegisterUser() throws Exception {
        mockMvc.perform(post("/users/register")
                .param("username", TEST_USER_NAME)
                .param("fullName", "testov")
                .param("email", "123@abv.bg")
                .param("age", "20")
                .param("password", "12345")
                .param("confirmPassword", "12345")
                .param("city", "СОФИЯ")
                .with(csrf())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(status().is3xxRedirection());

        Optional<UserEntity> byUsername = userRepository.findByUsername(TEST_USER_NAME);
        Assertions.assertEquals(3, userRepository.count());


        Assertions.assertTrue(byUsername.isPresent());

        Assertions.assertEquals(20,byUsername.get().getAge());
        Assertions.assertEquals("123@abv.bg",byUsername.get().getEmail());
        Assertions.assertEquals("testov",byUsername.get().getFullName());
    }
}