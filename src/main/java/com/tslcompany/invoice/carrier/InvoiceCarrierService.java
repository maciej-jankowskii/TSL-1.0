package com.tslcompany.invoice.carrier;

import com.tslcompany.order.Order;
import com.tslcompany.order.OrderRepository;
import com.tslcompany.order.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class InvoiceCarrierService {

    private final InvoiceCarrierRepository invoiceCarrierRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final InvoiceCarrierMapper invoiceCarrierMapper;

    public InvoiceCarrierService(InvoiceCarrierRepository invoiceCarrierRepository, OrderService orderService, OrderRepository orderRepository, InvoiceCarrierMapper invoiceCarrierMapper) {
        this.invoiceCarrierRepository = invoiceCarrierRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.invoiceCarrierMapper = invoiceCarrierMapper;
    }

    public List<InvoiceFromCarrier> findAllInvoices(){
        return (List<InvoiceFromCarrier>) invoiceCarrierRepository.findAll();
    }
    @Transactional
    public InvoiceCarrierDto addInvoiceFromCarrier(InvoiceCarrierDto invoiceCarrierDto){
        InvoiceFromCarrier invoice = invoiceCarrierMapper.map(invoiceCarrierDto);
        Long orderId = invoice.getOrder().getId();
        Order order = orderService.findById(orderId).orElseThrow(() -> new NoSuchElementException("Brak takiego zlecenia"));
        order.setInvoiced(true);
        orderRepository.save(order);

        InvoiceFromCarrier saved = invoiceCarrierRepository.save(invoice);
        return invoiceCarrierMapper.map(saved);
    }

}
