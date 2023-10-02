package com.tslcompany.controllers;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.cargo.CargoDto;
import com.tslcompany.cargo.CargoService;
import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.customer.carrier.CarrierDto;
import com.tslcompany.customer.carrier.CarrierRepository;
import com.tslcompany.customer.carrier.CarrierService;
import com.tslcompany.customer.client.Client;
import com.tslcompany.customer.client.ClientDto;
import com.tslcompany.customer.client.ClientRepository;
import com.tslcompany.customer.client.ClientService;
import com.tslcompany.details.OrderStatus;
import com.tslcompany.order.Order;
import com.tslcompany.order.OrderDto;
import com.tslcompany.order.OrderService;
import com.tslcompany.user.User;
import com.tslcompany.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class ForwarderController {

    private final ClientService clientService;
    private final CarrierService carrierService;

    private final CargoService cargoService;
    private final OrderService orderService;
    private final UserService userService;
    private final ClientRepository clientRepository;
    private final CarrierRepository carrierRepository;

    public ForwarderController(ClientService clientService, CarrierService carrierService, CargoService cargoService, OrderService orderService, UserService userService, ClientRepository clientRepository, CarrierRepository carrierRepository) {
        this.clientService = clientService;
        this.carrierService = carrierService;
        this.cargoService = cargoService;
        this.orderService = orderService;
        this.userService = userService;
        this.clientRepository = clientRepository;
        this.carrierRepository = carrierRepository;
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
        try {
            cargoService.addCargo(cargoDto);
            return "redirect:/add-cargo";
        } catch (IllegalArgumentException e){
            return "redirect:/date-error";
        }
    }

    @GetMapping("/show-all-cargos")
    public String cargosForm(Model model) {
        List<Cargo> allCargos = cargoService.findAllCargos();
        model.addAttribute("allCargos", allCargos);
        return "cargos-list";
    }

    @GetMapping("/show-all-orders")
    public String ordersForm(Model model, Authentication authentication) {
        User user = userService.findUser(authentication.getName()).orElseThrow(() -> new NoSuchElementException("Brak użytkownika"));
        List<Order> userOrders = orderService.findOrdersByUser(user);
        model.addAttribute("allOrders", userOrders);
        return "orders-list";
    }

    @GetMapping("/add-order")
    public String orderForm(Model model) {
        List<Carrier> aLlCarriers = carrierService.findALlCarriers();
        List<Cargo> freeCargos = cargoService.findFreeCargos();

        model.addAttribute("allCargos", freeCargos);
        model.addAttribute("allCarriers", aLlCarriers);
        return "add-order";
    }

    @PostMapping("/save-order")
    public String saveOrder(@ModelAttribute("orderDto") OrderDto orderDto, Authentication authentication) {
        String userName = authentication.getName();

        orderService.createOrder(orderDto, userName);
        return "redirect:/add-order";
    }

    @PostMapping("/update-order-status")
    public String changeStatusOfOrder(@RequestParam("orderId") Long orderId, @RequestParam("newOrderStatus") OrderStatus newStatus) {
        try {
            OrderStatus status = OrderStatus.valueOf(String.valueOf(newStatus));
            orderService.changeOrderStatus(orderId, status);
            return "redirect:/order-status-confirmation";
        } catch (NoSuchElementException | IllegalStateException e){
            return "redirect:/order-error";
        }
    }

    @GetMapping("/order-status-confirmation")
    public String changeStatusConfirmation() {
        return "order-status-confirmation";
    }
    @PostMapping("/edit-cargo")
    public String editCargo(@RequestParam Long id, Model model){
        Cargo cargo  = cargoService.findCargo(id).orElseThrow(() -> new NoSuchElementException("Brak ładunku"));
        model.addAttribute("cargo", cargo);
        return "edit-cargo-form";
    }

    @PostMapping("/update-cargo")
    public String updateCargo(@ModelAttribute("cargoDto") CargoDto cargoDto){
        try {
            cargoService.editCargo(cargoDto.getId(), cargoDto);
            return "redirect:/show-all-cargos";
        }catch (NoSuchElementException | IllegalStateException e){
            return "redirect:/cargo-error";
        }
    }
    @PostMapping("/edit-order")
    public String editOrder(@RequestParam Long id, Model model){
        Order order = orderService.findById(id).orElseThrow(() -> new NoSuchElementException("Brak ładunku"));
        model.addAttribute("order", order);
        return "edit-order-form";
    }
    @PostMapping("/update-order")
    public String updateOrder(@ModelAttribute("orderDto") OrderDto orderDto){
        try {
            orderService.updateOrder(orderDto.getId(), orderDto);
            return "redirect:/show-all-orders";
        } catch (NoSuchElementException | IllegalStateException e){
            return "redirect:/order-error";
        }

    }
}
