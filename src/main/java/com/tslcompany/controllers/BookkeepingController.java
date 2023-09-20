package com.tslcompany.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookkeepingController {

    @GetMapping("/bookkeeping")
    public String bookkeepingForm() {
        return "bookkeeping-panel";
    }
}
