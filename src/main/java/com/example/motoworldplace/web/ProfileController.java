package com.example.motoworldplace.web;

import com.example.motoworldplace.model.binding.UserProfileUpdateBindingModel;
import com.example.motoworldplace.model.service.UserServiceModel;
import com.example.motoworldplace.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

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

    @GetMapping("/profile/{id}/edit")
    public String editProfile(@PathVariable Long id, Model model){
        UserServiceModel userServiceModel = this.userService.findById(id).orElse(null);
        UserProfileUpdateBindingModel userProfileUpdateModel = modelMapper.map(userServiceModel, UserProfileUpdateBindingModel.class);
        userProfileUpdateModel.setPictureUrl(userServiceModel.getPictureUrl().getUrl());
        model.addAttribute("profile",userProfileUpdateModel);
        return "edit-profile";

    }

    @PatchMapping("/profile/{id}/edit")
    public String editProfile(@PathVariable Long id,
                              @Valid UserProfileUpdateBindingModel userProfileUpdateModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("profile", userProfileUpdateModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileUpdateModel", bindingResult);

            return "redirect:editProfile";
        }

        // TODO: 11/5/2021 edit in to user service
        var sad = userProfileUpdateModel;

        return "redirect:showProfile";
    }

    @ModelAttribute
    public UserProfileUpdateBindingModel userProfileUpdateModel(){
        return new UserProfileUpdateBindingModel();
    }
}
