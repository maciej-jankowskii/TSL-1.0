package com.tslcompany.invoice.carrier;

import com.tslcompany.order.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvoiceCarrierService {

    private final InvoiceCarrierRepository invoiceCarrierRepository;
    private final OrderService orderService;
    private final InvoiceCarrierMapper invoiceCarrierMapper;

    public InvoiceCarrierService(InvoiceCarrierRepository invoiceCarrierRepository, OrderService orderService, InvoiceCarrierMapper invoiceCarrierMapper) {
        this.invoiceCarrierRepository = invoiceCarrierRepository;
        this.orderService = orderService;
        this.invoiceCarrierMapper = invoiceCarrierMapper;
    }

    public List<InvoiceFromCarrier> findAllInvoices(){
        return (List<InvoiceFromCarrier>) invoiceCarrierRepository.findAll();
    }
    @Transactional
    public InvoiceCarrierDto addInvoiceFromCarrier(InvoiceCarrierDto invoiceCarrierDto){
        InvoiceFromCarrier invoice = invoiceCarrierMapper.map(invoiceCarrierDto);

        InvoiceFromCarrier saved = invoiceCarrierRepository.save(invoice);
        return invoiceCarrierMapper.map(saved);
    }


}
