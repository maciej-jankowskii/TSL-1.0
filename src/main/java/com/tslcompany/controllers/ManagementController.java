package com.tslcompany.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagementController {

    @GetMapping("/management")
    public String managementForm() {
        return "management-panel";
    }
}
