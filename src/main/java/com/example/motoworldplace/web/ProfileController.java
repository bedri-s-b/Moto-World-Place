package com.example.motoworldplace.web;

import com.example.motoworldplace.model.service.UserServiceModel;
import com.example.motoworldplace.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class ProfileController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProfileController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal User user, Model model){
        UserServiceModel userServiceModel = this.userService.findByUsername(user.getUsername()).orElse(null);
        model.addAttribute("profile",userServiceModel);
        return "profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(@AuthenticationPrincipal User user, Model model){
        UserServiceModel userServiceModel = this.userService.findByUsername(user.getUsername()).orElse(null);
        model.addAttribute("profile",userServiceModel);
        return "edit-profile";

    }
}
