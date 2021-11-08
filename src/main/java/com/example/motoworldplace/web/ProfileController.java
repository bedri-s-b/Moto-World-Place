package com.example.motoworldplace.web;

import com.example.motoworldplace.model.binding.UserProfileUpdateBindingModel;
import com.example.motoworldplace.model.service.UserServiceModel;
import com.example.motoworldplace.service.PictureService;
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
import java.io.IOException;

@Controller
@RequestMapping("/users")
public class ProfileController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PictureService pictureService;


    public ProfileController(UserService userService, ModelMapper modelMapper, PictureService pictureService) {
        this.userService = userService;
        this.modelMapper = modelMapper;

        this.pictureService = pictureService;
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
        userProfileUpdateModel.setPictureUrl(userServiceModel.getPicture().getUrl());
        model.addAttribute("profile",userProfileUpdateModel);
        return "edit-profile";

    }

    @PatchMapping("/profile/{id}/edit")
    public String editProfile(@PathVariable Long id,
                              @Valid UserProfileUpdateBindingModel userProfileUpdateModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes
                             ) throws IOException {
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("profile", userProfileUpdateModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileUpdateModel", bindingResult);

            return "redirect:editProfile";
        }

        userService.editProfile(userProfileUpdateModel);

        return "redirect:/users/profile";
    }

    @ModelAttribute
    public UserProfileUpdateBindingModel userProfileUpdateModel(){
        return new UserProfileUpdateBindingModel();
    }
}
