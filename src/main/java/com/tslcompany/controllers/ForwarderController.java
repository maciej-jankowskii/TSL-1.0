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
import com.tslcompany.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
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
        Order order = orderService.findById(orderId).orElseThrow(() -> new NoSuchElementException("Brak zlecenia o takim ID"));
        OrderStatus status = OrderStatus.valueOf(String.valueOf(newStatus));
        orderService.changeOrderStatus(orderId, status);

        if (OrderStatus.CANCELED.equals(newStatus)) {
            Cargo cargo = order.getCargo();
            Client client = cargo.getClient();
            Carrier carrier = order.getCarrier();

            BigDecimal price = order.getPrice();
            BigDecimal cargoPrice = order.getCargo().getPrice();

            BigDecimal clientBalance = client.getBalance();
            BigDecimal carrierBalance = carrier.getBalance();

            client.setBalance(clientBalance.subtract(cargoPrice));
            carrier.setBalance(carrierBalance.subtract(price));

            orderService.deleteById(orderId);
            cargoService.deleteById(cargo.getId());


            clientRepository.save(client);
            carrierRepository.save(carrier);
        }



        return "redirect:/order-status-confirmation";
    }
    @GetMapping("/order-status-confirmation")
    public String changeStatusConfirmation(){
        return "order-status-confirmation";
    }




}
