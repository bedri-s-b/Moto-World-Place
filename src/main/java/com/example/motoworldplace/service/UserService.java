package com.example.motoworldplace.service;

import com.example.motoworldplace.model.service.UserServiceModel;

import java.util.Optional;

public interface UserService {
    boolean existUsername(String username);

    void registerUser(UserServiceModel userService);

    void initAdmin();

    UserServiceModel findByUsernameAndPassword(String username, String password);

    Optional<UserServiceModel> findByUsername(String username);

}
