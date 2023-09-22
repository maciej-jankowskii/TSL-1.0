package com.tslcompany.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomePageController {

    @GetMapping("/home-page")
    public String homeForm(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        return "home-page";
    }


}
