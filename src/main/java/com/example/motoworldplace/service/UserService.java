package com.example.motoworldplace.service;

import com.example.motoworldplace.model.binding.UserProfileUpdateBindingModel;
import com.example.motoworldplace.model.entity.GroupEntity;
import com.example.motoworldplace.model.entity.UserEntity;
import com.example.motoworldplace.model.service.UserServiceModel;
import com.example.motoworldplace.model.view.UserViewModel;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UserService {
    boolean existUsername(String username);

    void registerUser(UserServiceModel userService);

    void initAdmin();

    Optional<UserServiceModel> findByUsername(String username, Principal principal);

    Optional<UserServiceModel> findById(Long id);

    void replacePic(Long userId, String url);

    void editProfile(UserProfileUpdateBindingModel userProfileUpdateModel) throws IOException;

    UserEntity findByUserName(String admin);

    void addUser(UserEntity user, GroupEntity group);

    UserViewModel findUserViewModelByUsername(String name);

    boolean isOwner(String username,Long id);


    List<UserViewModel> findAllUsers();
}
