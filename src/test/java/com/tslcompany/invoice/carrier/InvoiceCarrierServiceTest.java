package com.tslcompany.invoice.carrier;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.customer.carrier.CarrierRepository;
import com.tslcompany.customer.client.Client;
import com.tslcompany.order.Order;
import com.tslcompany.order.OrderRepository;
import com.tslcompany.order.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InvoiceCarrierServiceTest {

    @Mock private InvoiceCarrierRepository invoiceCarrierRepository;
    @Mock private OrderService orderService;
    @Mock private OrderRepository orderRepository;
    @Mock private InvoiceCarrierMapper invoiceCarrierMapper;
    @Mock private CarrierRepository carrierRepository;

    @InjectMocks
    private InvoiceCarrierService invoiceCarrierService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllInvoices(){
        List<InvoiceFromCarrier> invoices = createListInvoices();

        when(invoiceCarrierRepository.findAll()).thenReturn(invoices);

        List<InvoiceFromCarrier> allInvoices = invoiceCarrierService.findAllInvoices();

        assertNotNull(allInvoices);
        assertEquals(4, allInvoices.size());
        assertEquals(invoices, allInvoices);
    }

    @Test
    public void testFilterPaidInvoices(){
        List<InvoiceFromCarrier> invoices = createListInvoices();

        when(invoiceCarrierRepository.findAll()).thenReturn(invoices);

        List<InvoiceFromCarrier> paidInvoices = invoiceCarrierService.filterInvoices(true);

        assertNotNull(paidInvoices);
        assertEquals(2, paidInvoices.size());
        assertTrue(paidInvoices.get(0).isPaid());
        assertTrue(paidInvoices.get(1).isPaid());

    }

    @Test
    public void testFilterUnpaidInvoices(){
        List<InvoiceFromCarrier> invoices = createListInvoices();

        when(invoiceCarrierRepository.findAll()).thenReturn(invoices);

        List<InvoiceFromCarrier> paidInvoices = invoiceCarrierService.filterInvoices(false);

        assertNotNull(paidInvoices);
        assertEquals(2, paidInvoices.size());
        assertFalse(paidInvoices.get(0).isPaid());
        assertFalse(paidInvoices.get(1).isPaid());

    }

    @Test
    public void testAddInvoiceFromCarrier(){
        InvoiceCarrierDto invoiceDto = createInvoiceCarrierDto();
        InvoiceFromCarrier invoice = createInvoiceFromCarrier();
        LocalDate currentDate = LocalDate.now();
        Order order = createOrder();
        Carrier carrier = createCarrier();
        Cargo cargo = createCargo();
        order.setId(1L);
        order.setCarrier(carrier);
        order.setCargo(cargo);
        invoice.setOrder(order);
        invoice.setCarrier(carrier);
        Integer termOfPayment = 30;
        carrier.setTermOfPayment(termOfPayment);
        invoice.setInvoiceDate(currentDate);


        when(invoiceCarrierMapper.map(invoiceDto)).thenReturn(invoice);
        when(orderService.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(carrierRepository.save(carrier)).thenReturn(carrier);
        when(invoiceCarrierRepository.save(invoice)).thenReturn(invoice);
        when(invoiceCarrierMapper.map(invoice)).thenReturn(invoiceDto);

        InvoiceCarrierDto result = invoiceCarrierService.addInvoiceFromCarrier(invoiceDto);

        assertNotNull(result);
        order.setInvoiced(true);
        verify(orderRepository).save(order);

        LocalDate dueDate = currentDate.plusDays(termOfPayment);
        assertEquals(dueDate, invoice.getDueDate());

        verify(invoiceCarrierRepository).save(invoice);


    }

    @Test
    public void testPayInvoice(){
        InvoiceFromCarrier invoice = createInvoiceFromCarrier();
        invoice.setId(1L);
        invoice.setNettoValue(BigDecimal.valueOf(1000));
        Carrier carrier = createCarrier();
        invoice.setCarrier(carrier);


        when(invoiceCarrierRepository.findById(invoice.getId())).thenReturn(Optional.of(invoice));
        when(invoiceCarrierRepository.save(invoice)).thenReturn(invoice);
        when(carrierRepository.save(carrier)).thenReturn(carrier);


        invoiceCarrierService.payInvoice(invoice.getId());

        assertTrue(invoice.isPaid());
        verify(invoiceCarrierRepository).save(invoice);
        BigDecimal expectedBalance = BigDecimal.valueOf(4000);
        assertEquals(expectedBalance, carrier.getBalance());
        verify(carrierRepository).save(carrier);


    }

    private Cargo createCargo() {
        Cargo cargo = new Cargo();
        return cargo;
    }

    private InvoiceCarrierDto createInvoiceCarrierDto() {
        InvoiceCarrierDto dto = new InvoiceCarrierDto();
        return dto;
    }


    private InvoiceFromCarrier createInvoiceFromCarrier() {
        InvoiceFromCarrier invoice = new InvoiceFromCarrier();
        return invoice;
    }


    private Order createOrder() {
        Order order = new Order();
        return order;
    }


    private Carrier createCarrier() {
        Carrier carrier = new Carrier();
        carrier.setId(1L);
        carrier.setBalance(BigDecimal.valueOf(5000));
        return carrier;
    }


    private List<InvoiceFromCarrier> createListInvoices() {
        List<InvoiceFromCarrier> invoices = new ArrayList<>();
        InvoiceFromCarrier invoice1 = new InvoiceFromCarrier();
        InvoiceFromCarrier invoice2 = new InvoiceFromCarrier();
        InvoiceFromCarrier invoice3 = new InvoiceFromCarrier();
        InvoiceFromCarrier invoice4 = new InvoiceFromCarrier();

        invoice1.setPaid(true);
        invoice2.setPaid(true);
        invoice3.setPaid(false);
        invoice4.setPaid(false);

        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);
        return invoices;
    }

}