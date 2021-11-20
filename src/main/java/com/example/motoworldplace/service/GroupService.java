package com.example.motoworldplace.service;

import com.example.motoworldplace.model.binding.GroupAddBindingModel;
import com.example.motoworldplace.model.service.GroupServiceModel;
import com.example.motoworldplace.model.service.UserServiceModel;
import com.example.motoworldplace.model.view.GroupViewModel;
import com.example.motoworldplace.model.view.UserViewModel;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

public interface GroupService {
    void initGroup();

    List<GroupViewModel> findAllGroup(Principal principal);

    GroupServiceModel addGroup(GroupAddBindingModel groupAddBindingModel, String name) throws IOException;

    boolean findByName(String name);

    GroupViewModel findGroupViewModelById(Long id, UserViewModel userViewModel);

    void joinMemberToGroup(Long id, String name, String otherName);

    List<GroupViewModel> findAllGroupWhichAdmin(String name);

    boolean isMember(String username, Long id);
}
