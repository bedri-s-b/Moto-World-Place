package com.example.motoworldplace.service;

import com.example.motoworldplace.model.binding.UserProfileUpdateBindingModel;
import com.example.motoworldplace.model.service.UserServiceModel;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
    boolean existUsername(String username);

    void registerUser(UserServiceModel userService);

    void initAdmin();

    UserServiceModel findByUsernameAndPassword(String username, String password);

    Optional<UserServiceModel> findByUsername(String username);

    Optional<UserServiceModel> findById(Long id);

    void replacePic(Long userId, String url);

    void editProfile(UserProfileUpdateBindingModel userProfileUpdateModel) throws IOException;
}
