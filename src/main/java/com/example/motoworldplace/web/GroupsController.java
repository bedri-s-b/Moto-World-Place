package com.example.motoworldplace.web;

import com.example.motoworldplace.model.binding.GroupAddBindingModel;
import com.example.motoworldplace.model.service.GroupServiceModel;
import com.example.motoworldplace.model.service.UserServiceModel;
import com.example.motoworldplace.model.view.GroupViewModel;
import com.example.motoworldplace.model.view.UserViewModel;
import com.example.motoworldplace.service.GroupService;
import com.example.motoworldplace.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/groups")
public class GroupsController {
    private final GroupService groupService;
    private final UserService userService;

    public GroupsController(GroupService groupService, UserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public String allGroups(Model model, Principal principal) {
        model.addAttribute("groups", groupService.findAllGroup(principal));
        return "groups";
    }

    @GetMapping("/add")
    public String getAddGroup(Model model) {
        if (!model.containsAttribute("exist")) {
            model.addAttribute("exist", false);
        }
        return "add-group";
    }

    @GetMapping("/my")
    public String myGroups(Model model, Principal principal) {
        List<GroupViewModel> allGroupWhichAdmin = groupService.findAllGroupWhichAdmin(principal.getName());
        model.addAttribute("groups", allGroupWhichAdmin);
        return "groups";
    }


    @PostMapping("/add")
    public String addGroup(@Valid GroupAddBindingModel groupAddBindingModel
            , BindingResult bindingResult
            , RedirectAttributes redirectAttributes
            , Principal principal
    ) throws IOException {

        boolean existName = groupService.findByName(groupAddBindingModel.getName());
        if (bindingResult.hasErrors() || existName) {
            redirectAttributes.addFlashAttribute("groupAddBindingModel", groupAddBindingModel)
                    .addFlashAttribute("org.springframework.validation.BindingResult.groupAddBindingModel", bindingResult);
            if (existName) {
                redirectAttributes.addFlashAttribute("exist", true);
            }
            return "redirect:/groups/add";
        }

        groupService.addGroup(groupAddBindingModel, principal.getName());
        return "redirect:/groups/all";
    }

    @GetMapping("/{id}/join")
    public String getJoinGroup(@PathVariable("id") Long id, Model model, Principal principal) {
        UserViewModel user = userService.findUserViewModelByUsername(principal.getName());
        GroupViewModel group = groupService.findGroupViewModelById(id, user);
        model.addAttribute("user", user);
        model.addAttribute("group", group);

        return "request-to-member-group";
    }

    @PostMapping("/{id}/join")
    public String joinGroup(@PathVariable("id") Long id, @RequestParam String otherName, Principal principal) {
        groupService.joinMemberToGroup(id, principal.getName(), otherName);
        return "redirect:/groups/all";
    }

    @PreAuthorize("isMember(#id)")
    @GetMapping("/{id}/group")
    public String getGroup(@PathVariable("id") Long id, Model model, Principal principal) {
        UserViewModel user = userService.findUserViewModelByUsername(principal.getName());
        GroupViewModel groupViewModelById = groupService.findGroupViewModelById(id, user);
        model.addAttribute("group", groupViewModelById);
        List<UserServiceModel> members = groupViewModelById.getMembersName().stream().map(name -> {
            return userService.findByUsername(name, principal).get();
        }).collect(Collectors.toList());
        model.addAttribute("members", members);

        return "group";
    }


    @ModelAttribute
    public GroupAddBindingModel groupAddBindingModel() {
        return new GroupAddBindingModel();
    }

}
