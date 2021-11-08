package com.example.motoworldplace.init;

import com.example.motoworldplace.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInit implements CommandLineRunner {
    private final UserService userService;
    private final GroupService groupService;
    private final CityService cityService;
    private final PictureService pictureService;

    public DatabaseInit(UserService userService, GroupService groupService, CityService cityService, PictureService pictureService) {
        this.userService = userService;
        this.groupService = groupService;
        this.cityService = cityService;
        this.pictureService = pictureService;
    }


    @Override
    public void run(String... args) throws Exception {
        pictureService.initPic();
        cityService.initCities();
        groupService.initGroup();
        userService.initAdmin();
    }
}
