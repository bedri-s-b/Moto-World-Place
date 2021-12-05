package com.example.motoworldplace.web;


import com.example.motoworldplace.model.entity.EventEntity;
import com.example.motoworldplace.model.entity.GroupEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import com.example.motoworldplace.model.entity.enums.RoleEnum;
import com.example.motoworldplace.repository.EventRepository;
import com.example.motoworldplace.repository.GroupRepository;
import com.example.motoworldplace.repository.MessageRepository;
import com.example.motoworldplace.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import static org.junit.jupiter.api.Assertions.*;

@WithMockUser("Admin")
@SpringBootTest
@AutoConfigureMockMvc
class EventsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GroupRepository groupRepository;

    @BeforeEach
    void init() {
        testEvent = new EventEntity().setTitle("testEvent")
                .setDescription("test description")
                .setStared(LocalDateTime.now())
                .setGroup(groupRepository.findByName("FREE_RAIDER"))
                .setCreator(userRepository.getById(1L))
                .setMembersCome(Set.of(userRepository.getById(1L)));
        eventRepository.save(testEvent);

    }

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
    }

    private EventEntity testEvent;


    @Test
    void testOpenRegisterForm() throws Exception {

        mockMvc.
                perform(get("/groups/" + testEvent.getGroup().getId() + "/group/events/" + testEvent.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("event"));
    }

    @Test
    void tesGetAllEvents() throws Exception {

        mockMvc.
                perform(get("/groups/" + testEvent.getGroup().getId() + "/group/events/"))
                .andExpect(status().isOk())
                .andExpect(view().name("events"));
    }

    @Test
    void testAddEvent() throws Exception {
        mockMvc.perform(post("/groups/" + groupRepository.findById(1L).get().getId() + "/group/events/add")
                .param("title", "test title")
                .param("description", "test description 2")
                .param("started", LocalDateTime.now().plusHours(4).toString())
                .with(csrf()).contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(status().is3xxRedirection());
        Assertions.assertEquals(2, eventRepository.count());

        List<EventEntity> eventsByGroupNameOrderByStared = eventRepository.findEventsByGroupNameOrderByStared(groupRepository.findById(1L).get().getName());

        List<String> collect = eventsByGroupNameOrderByStared.stream().map(EventEntity::getTitle).collect(Collectors.toList());
        Assertions.assertTrue(collect.contains("test title"));
    }
}