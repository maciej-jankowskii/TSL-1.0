package com.tslcompany.invoice.carrier;

import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.customer.carrier.CarrierRepository;
import com.tslcompany.order.Order;
import com.tslcompany.order.OrderRepository;
import com.tslcompany.order.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class InvoiceCarrierService {

    private final InvoiceCarrierRepository invoiceCarrierRepository;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final InvoiceCarrierMapper invoiceCarrierMapper;
    private final CarrierRepository carrierRepository;

    public InvoiceCarrierService(InvoiceCarrierRepository invoiceCarrierRepository, OrderService orderService, OrderRepository orderRepository, InvoiceCarrierMapper invoiceCarrierMapper, CarrierRepository carrierRepository) {
        this.invoiceCarrierRepository = invoiceCarrierRepository;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.invoiceCarrierMapper = invoiceCarrierMapper;
        this.carrierRepository = carrierRepository;
    }

    public List<InvoiceFromCarrier> findAllInvoices(){
        return (List<InvoiceFromCarrier>) invoiceCarrierRepository.findAll();
    }
    @Transactional
    public InvoiceCarrierDto addInvoiceFromCarrier(InvoiceCarrierDto invoiceCarrierDto){
        InvoiceFromCarrier invoice = invoiceCarrierMapper.map(invoiceCarrierDto);
        LocalDate currentDate = LocalDate.now();
        invoice.setInvoiceDate(currentDate);



        Long orderId = invoice.getOrder().getId();
        Order order = orderService.findById(orderId).orElseThrow(() -> new NoSuchElementException("Brak takiego zlecenia"));
        order.setInvoiced(true);
        orderRepository.save(order);


        Carrier carrier = invoice.getCarrier();
        if (carrier != null){
            Integer termOfPayment = carrier.getTermOfPayment();
            LocalDate dueDate = currentDate.plusDays(termOfPayment);
            invoice.setDueDate(dueDate);

        }

        InvoiceFromCarrier saved = invoiceCarrierRepository.save(invoice);
        return invoiceCarrierMapper.map(saved);
    }

    @Transactional
    public void payInvoice(Long invoiceId){
        InvoiceFromCarrier invoice = invoiceCarrierRepository.findById(invoiceId).orElseThrow(() -> new NoSuchElementException("Brak faktury"));
        if (invoice.isPaid()){
            throw new IllegalStateException("Faktura jest już opłacona");
        } else {
            invoice.setPaid(true);
            invoiceCarrierRepository.save(invoice);
            Carrier carrier = invoice.getCarrier();
            BigDecimal value = invoice.getNettoValue();
            BigDecimal newBalance = carrier.getBalance().subtract(value);
            carrier.setBalance(newBalance);
            carrierRepository.save(carrier);

        }
    }

}
