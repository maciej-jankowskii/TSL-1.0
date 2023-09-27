package com.tslcompany.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/error403-page")
    public String error403(){
        return "error/403";
    }
    @GetMapping("/error404-page")
    public String error404(){
        return "error/404";
    }
    @GetMapping("/error500-page")
    public String error500(){
        return "error/500";
    }
}
