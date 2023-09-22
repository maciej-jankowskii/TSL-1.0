package com.tslcompany.controllers;

import com.tslcompany.customer.carrier.CarrierDto;
import com.tslcompany.customer.carrier.CarrierService;
import com.tslcompany.customer.client.ClientDto;
import com.tslcompany.customer.client.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ForwarderController {

    private final ClientService clientService;
    private final CarrierService carrierService;

    public ForwarderController(ClientService clientService, CarrierService carrierService) {
        this.clientService = clientService;
        this.carrierService = carrierService;
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
    @GetMapping("/add-carrier")
    public String addCarrierForm(){
        return "add-carrier";
    }
    @PostMapping("/add-new-carrier")
    public String addCarrier(@ModelAttribute("carrierDto")CarrierDto carrierDto){
        CarrierDto saved = carrierService.addCarrier(carrierDto);
        return "redirect:/add-carrier";

    }
}
