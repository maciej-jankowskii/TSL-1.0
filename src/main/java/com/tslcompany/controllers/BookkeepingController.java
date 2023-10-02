package com.tslcompany.controllers;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.cargo.CargoService;
import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.customer.carrier.CarrierService;
import com.tslcompany.forwarder.ForwarderDto;
import com.tslcompany.forwarder.ForwarderService;
import com.tslcompany.invoice.carrier.InvoiceCarrierDto;
import com.tslcompany.invoice.carrier.InvoiceCarrierService;
import com.tslcompany.invoice.carrier.InvoiceFromCarrier;
import com.tslcompany.invoice.client.InvoiceClientDto;
import com.tslcompany.invoice.client.InvoiceClientService;
import com.tslcompany.invoice.client.InvoiceForClient;
import com.tslcompany.order.Order;
import com.tslcompany.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookkeepingController {

    private final InvoiceCarrierService invoiceCarrierService;
    private final InvoiceClientService invoiceClientService;
    private final OrderService orderService;
    private final CarrierService carrierService;
    private final CargoService cargoService;
    private final ForwarderService forwarderService;


    public BookkeepingController(InvoiceCarrierService invoiceCarrierService, InvoiceClientService invoiceClientService, OrderService orderService, CarrierService carrierService, CargoService cargoService, ForwarderService forwarderService) {
        this.invoiceCarrierService = invoiceCarrierService;
        this.invoiceClientService = invoiceClientService;
        this.orderService = orderService;
        this.carrierService = carrierService;
        this.cargoService = cargoService;
        this.forwarderService = forwarderService;
    }

    @GetMapping("/bookkeeping")
    public String bookkeepingForm() {
        return "bookkeeping-panel";
    }

    @GetMapping("/invoices-carrier")
    public String carrierInvoicesForm(Model model) {
        List<InvoiceFromCarrier> invoices = invoiceCarrierService.findAllInvoices();
        model.addAttribute("invoices", invoices);
        return "invoices-from-carrier";
    }

    @GetMapping("/add-invoice-carrier")
    public String newCarrierInvoice(Model model) {
        List<Carrier> carriers = carrierService.findALlCarriers();
        List<Order> noInvoicedOrders = orderService.findAllOrdersWithNoInvoice();
        model.addAttribute("carriers", carriers);
        model.addAttribute("orders", noInvoicedOrders);
        return "new-invoice-carrier";
    }

    @PostMapping("/add-new-invoice-carrier")
    public String addNewCarrierInvoice(@ModelAttribute("invoiceDto") InvoiceCarrierDto invoiceDto) {
        invoiceCarrierService.addInvoiceFromCarrier(invoiceDto);
        return "redirect:/invoices-carrier";
    }

    @PostMapping("/filter-invoices-carrier")
    public String showFilterCarrierInvoices(@RequestParam(name = "isPaid") String isPaid, Model model) {
        Boolean isPaidBoolean = "true".equals(isPaid);
        List<InvoiceFromCarrier> filteredInvoices = invoiceCarrierService.filterInvoices(isPaidBoolean);
        model.addAttribute("invoices", filteredInvoices);
        return "filtered-invoices-carrier-list";
    }

    @PostMapping("/filter-invoices-client")
    public String showFilterClientInvoices(@RequestParam(name = "isPaid") String isPaid, Model model) {
        Boolean isPaidBoolean = "true".equals(isPaid);
        List<InvoiceForClient> filteredInvoices = invoiceClientService.filterInvoices(isPaidBoolean);
        model.addAttribute("invoices", filteredInvoices);
        return "filtered-invoices-client-list";
    }


    @PostMapping("/pay-invoice-carrier")
    public String payInvoiceCarrier(@RequestParam("invoiceId") Long invoiceId) {
        invoiceCarrierService.payInvoice(invoiceId);
        return "redirect:/invoices-carrier";
    }

    @PostMapping("/pay-invoice-client")
    public String payInvoiceClient(@RequestParam("invoiceId") Long invoiceId) {
        invoiceClientService.payInvoice(invoiceId);
        return "redirect:/invoices-client";
    }


    @GetMapping("/invoices-client")
    public String clientInvoicesForm(Model model) {
        List<InvoiceForClient> invoices = invoiceClientService.findAllInvoices();
        model.addAttribute("invoices", invoices);
        return "invoices-for-client";
    }

    @GetMapping("/add-invoice-client")
    public String addNewClientInvoice(Model model) {
        List<Cargo> cargosWithNoInvoice = cargoService.findCargosWithoutClientInvoice();
        model.addAttribute("cargosNoInvoice", cargosWithNoInvoice);
        return "new-invoice-client";
    }

    @PostMapping("/add-new-invoice-client")
    public String addNewClientInvoice(@ModelAttribute("invoiceDto") InvoiceClientDto invoiceDto) {
        invoiceClientService.addInvoiceForClient(invoiceDto);
        return "redirect:/invoices-client";
    }

    @GetMapping("/forwarders-margin")
    public String forwardersMarginForm(Model model) {
        List<ForwarderDto> allForwarders = forwarderService.findAllForwarders();
        model.addAttribute("allForwarders", allForwarders);
        return "forwarders-margin";
    }


}
