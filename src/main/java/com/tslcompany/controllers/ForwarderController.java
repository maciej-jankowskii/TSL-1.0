package com.tslcompany.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForwarderController {

    @GetMapping("/forwarder")
    public String forwarderPanel(){
        return "forwarder-panel";
    }
}
