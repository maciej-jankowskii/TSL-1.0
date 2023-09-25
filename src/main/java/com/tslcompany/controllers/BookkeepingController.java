package com.tslcompany.controllers;

import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.customer.carrier.CarrierService;
import com.tslcompany.invoice.carrier.InvoiceCarrierDto;
import com.tslcompany.invoice.carrier.InvoiceCarrierService;
import com.tslcompany.invoice.carrier.InvoiceFromCarrier;
import com.tslcompany.order.Order;
import com.tslcompany.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookkeepingController {

    private final InvoiceCarrierService invoiceCarrierService;
    private final OrderService orderService;
    private final CarrierService carrierService;

    public BookkeepingController(InvoiceCarrierService invoiceCarrierService, OrderService orderService, CarrierService carrierService) {
        this.invoiceCarrierService = invoiceCarrierService;
        this.orderService = orderService;
        this.carrierService = carrierService;
    }

    @GetMapping("/bookkeeping")
    public String bookkeepingForm() {
        return "bookkeeping-panel";
    }

    @GetMapping("/invoices-carrier")
    public String carrierInvoicesForm(Model model){
        List<InvoiceFromCarrier> invoices = invoiceCarrierService.findAllInvoices();
        model.addAttribute("invoices", invoices);
        return "invoices-from-carrier";
    }

    @GetMapping("/add-invoice-carrier")
    public String newCarrierInvoice(Model model){
        List<Order> orders = orderService.findAllOrders();
        List<Carrier> carriers = carrierService.findALlCarriers();

        List<Order> noInvoicedOrders = orders.stream().filter(order -> order.isInvoiced() == false).collect(Collectors.toList());
        model.addAttribute("carriers", carriers);
        model.addAttribute("orders", noInvoicedOrders);
        return "new-invoice-carrier";
    }

    @PostMapping("/add-new-invoice-carrier")
    public String addNewCarrierInvoice(@ModelAttribute("invoiceDto") InvoiceCarrierDto invoiceDto){
        invoiceCarrierService.addInvoiceFromCarrier(invoiceDto);
        return "redirect:/invoices-carrier";
    }
}
