package com.tslcompany.controllers;

import com.tslcompany.customer.ClientDto;
import com.tslcompany.customer.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ForwarderController {

    private final ClientService clientService;

    public ForwarderController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/forwarder")
    public String forwarderPanel(){
        return "forwarder-panel";
    }
    @GetMapping("/add-client")
    public String addClientForm(){
        return "add-client";
    }

    @PostMapping("/add-new-client")
    public String addClient(@ModelAttribute("clientDto") ClientDto clientDto){
        ClientDto saved = clientService.addClient(clientDto);

        return "redirect:/add-client";

    }
}
