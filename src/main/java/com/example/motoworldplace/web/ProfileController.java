package com.example.motoworldplace.web;

import com.example.motoworldplace.model.binding.UserProfileUpdateBindingModel;
import com.example.motoworldplace.model.service.UserServiceModel;
import com.example.motoworldplace.service.PictureService;
import com.example.motoworldplace.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

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
    public String showProfile(@AuthenticationPrincipal User user, Model model, Principal principal) {
        UserServiceModel userServiceModel = this.userService.findByUsername(user.getUsername(), principal).orElse(null);
        model.addAttribute("profile", userServiceModel);
        return "profile";
    }

    @GetMapping("/profile/{id}")
    public String showProfileWithId(@PathVariable("id") Long id, Model model, Principal principal) {
        UserServiceModel user = this.userService.findById(id).orElse(null);
        UserServiceModel userServiceModel = this.userService.findByUsername(user.getUsername(), principal).orElse(null);
        model.addAttribute("profile", userServiceModel);
        return "profile";
    }

    @PreAuthorize("isOwner(#id)")
    @GetMapping("/profile/{id}/edit")
    public String editProfile(@PathVariable Long id, Model model) {

        UserServiceModel userServiceModel = this.userService.findById(id).orElse(null);
        UserProfileUpdateBindingModel userProfileUpdateBindingModel = modelMapper.map(userServiceModel, UserProfileUpdateBindingModel.class);
        model.addAttribute("userProfileUpdateBindingModel", userProfileUpdateBindingModel);
        model.addAttribute("picUrl", pictureService.findPictureUrlByUserId(id));
        return "edit-profile";

    }

    @GetMapping("/profile/{id}/edit/errors")
    public String editProfileError(@PathVariable Long id,Model model) {
        model.addAttribute("picUrl", pictureService.findPictureUrlByUserId(id));
        return "edit-profile";
    }

    @PreAuthorize("isOwner(#id)")
    @PatchMapping("/profile/{id}/edit")
    public String editProfile(@PathVariable Long id,
                              @Valid UserProfileUpdateBindingModel userProfileUpdateBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userProfileUpdateBindingModel", userProfileUpdateBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.userProfileUpdateBindingModel", bindingResult);

            return "redirect:/users/profile/" + id + "/edit/errors";
        }

        userService.editProfile(userProfileUpdateBindingModel);

        return "redirect:/users/profile";
    }


    @ModelAttribute
    public UserProfileUpdateBindingModel userProfileUpdateModel() {
        return new UserProfileUpdateBindingModel();
    }
}
