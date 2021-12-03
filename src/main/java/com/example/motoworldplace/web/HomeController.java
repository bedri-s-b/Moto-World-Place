package com.example.motoworldplace.web;

import com.example.motoworldplace.model.view.GroupViewModel;
import com.example.motoworldplace.service.GroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "index";
    }
}
