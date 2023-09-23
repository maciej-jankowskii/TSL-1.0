package com.tslcompany.controllers;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.cargo.CargoDto;
import com.tslcompany.cargo.CargoService;
import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.customer.carrier.CarrierDto;
import com.tslcompany.customer.carrier.CarrierService;
import com.tslcompany.customer.client.Client;
import com.tslcompany.customer.client.ClientDto;
import com.tslcompany.customer.client.ClientService;
import com.tslcompany.details.OrderStatus;
import com.tslcompany.order.Order;
import com.tslcompany.order.OrderDto;
import com.tslcompany.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ForwarderController {

    private final ClientService clientService;
    private final CarrierService carrierService;

    private final CargoService cargoService;
    private final OrderService orderService;

    public ForwarderController(ClientService clientService, CarrierService carrierService, CargoService cargoService, OrderService orderService) {
        this.clientService = clientService;
        this.carrierService = carrierService;
        this.cargoService = cargoService;
        this.orderService = orderService;
    }

    @GetMapping("/forwarder")
    public String forwarderPanel() {
        return "forwarder-panel";
    }

    @GetMapping("/add-client")
    public String addClientForm() {
        return "add-client";
    }

    @PostMapping("/add-new-client")
    public String addClient(@ModelAttribute("clientDto") ClientDto clientDto) {
        ClientDto saved = clientService.addClient(clientDto);

        return "redirect:/add-client";

    }

    @GetMapping("/add-carrier")
    public String addCarrierForm() {
        return "add-carrier";
    }

    @PostMapping("/add-new-carrier")
    public String addCarrier(@ModelAttribute("carrierDto") CarrierDto carrierDto) {
        CarrierDto saved = carrierService.addCarrier(carrierDto);
        return "redirect:/add-carrier";

    }

    @GetMapping("/add-cargo")
    public String addCargoFrom(Model model) {
        List<Client> clients = clientService.findAllClients();
        model.addAttribute("clients", clients);
        return "add-cargo";
    }

    @PostMapping("/add-new-cargo")
    public String addCargo(@ModelAttribute("cargoDto") CargoDto cargoDto) {
        cargoService.addCargo(cargoDto);
        return "redirect:/add-cargo";
    }

    @GetMapping("/show-all-cargos")
    public String cargosForm(Model model) {
        List<Cargo> allCargos = cargoService.findAllCargos();
        model.addAttribute("allCargos", allCargos);
        return "cargos-list";
    }

    @GetMapping("/show-all-orders")
    public String ordersForm(Model model) {
        List<Order> allOrders = orderService.findAllOrders();
        model.addAttribute("allOrders", allOrders);
        return "orders-list";
    }

    @GetMapping("/add-order")
    public String orderForm(Model model) {
        List<Cargo> allCargos = cargoService.findAllCargos();
        List<Carrier> aLlCarriers = carrierService.findALlCarriers();
        model.addAttribute("allCargos", allCargos);
        model.addAttribute("allCarriers", aLlCarriers);
        return "add-order";
    }

    @PostMapping("/save-order")
    public String saveOrder(@ModelAttribute("orderDto") OrderDto orderDto) {
        orderService.createOrder(orderDto);
        return "redirect:/add-order";

    }
    @PostMapping("/update-order-status")
    public String changeStatusOfOrder(@RequestParam("orderId") Long orderId, @RequestParam("newOrderStatus") OrderStatus newStatus){
        OrderStatus status = OrderStatus.valueOf(String.valueOf(newStatus));
        orderService.changeOrderStatus(orderId, status);

        return "redirect:/order-status-confirmation";
    }
    @GetMapping("/order-status-confirmation")
    public String changeStatusConfirmation(){
        return "order-status-confirmation";
    }




}
