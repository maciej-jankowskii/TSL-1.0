package com.tslcompany.invoice.client;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.cargo.CargoRepository;
import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.customer.client.Client;
import com.tslcompany.customer.client.ClientRepository;
import com.tslcompany.invoice.carrier.InvoiceCarrierDto;
import com.tslcompany.invoice.carrier.InvoiceFromCarrier;
import com.tslcompany.order.Order;
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

class InvoiceClientServiceTest {

    @Mock
    private InvoiceClientRepository invoiceClientRepository;
    @Mock
    private InvoiceClientMapper invoiceClientMapper;
    @Mock
    private CargoRepository cargoRepository;
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    InvoiceClientService invoiceClientService;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllInvoices(){
        List<InvoiceForClient> invoices = createListInvoices();

        when(invoiceClientRepository.findAll()).thenReturn(invoices);

        List<InvoiceForClient> result = invoiceClientService.findAllInvoices();

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(result, invoices);

    }

    @Test
    public void testFilterPaidInvoices(){
        List<InvoiceForClient> invoices = createListInvoices();

        when(invoiceClientRepository.findAll()).thenReturn(invoices);

        List<InvoiceForClient> paidInvoices = invoiceClientService.filterInvoices(true);

        assertNotNull(paidInvoices);
        assertEquals(2, paidInvoices.size());
        assertTrue(paidInvoices.get(0).isPaid());
        assertTrue(paidInvoices.get(1).isPaid());
    }

    @Test
    public void testFilterUnpaidInvoices(){
        List<InvoiceForClient> invoices = createListInvoices();

        when(invoiceClientRepository.findAll()).thenReturn(invoices);

        List<InvoiceForClient> paidInvoices = invoiceClientService.filterInvoices(false);

        assertNotNull(paidInvoices);
        assertEquals(2, paidInvoices.size());
        assertFalse(paidInvoices.get(0).isPaid());
        assertFalse(paidInvoices.get(1).isPaid());
    }

    @Test
    public void testAddInvoice(){
        InvoiceClientDto invoiceDto = createInvoiceClientDto();
        InvoiceForClient invoice = createInvoiceForClient();
        LocalDate currentDate = LocalDate.now();
        Cargo cargo = createCargo();
        Client client = createClient();
        cargo.setId(1L);
        cargo.setClient(client);
        invoice.setCargo(cargo);
        invoice.setInvoiceDate(currentDate);
        Integer termOfPayment = 30;
        client.setTermOfPayment(termOfPayment);

        when(invoiceClientMapper.map(invoiceDto)).thenReturn(invoice);
        when(cargoRepository.findById(cargo.getId())).thenReturn(Optional.of(cargo));
        when(cargoRepository.save(cargo)).thenReturn(cargo);
        when(invoiceClientRepository.save(invoice)).thenReturn(invoice);
        when(invoiceClientMapper.map(invoice)).thenReturn(invoiceDto);

        InvoiceClientDto result = invoiceClientService.addInvoiceForClient(invoiceDto);

        assertNotNull(result);
        cargo.setInvoicedForClient(true);
        verify(cargoRepository).save(cargo);

        LocalDate dueDate = currentDate.plusDays(termOfPayment);
        assertEquals(dueDate, invoice.getDueDate());

    }

    @Test
    public void testPayInvoice(){
        InvoiceForClient invoice = createInvoiceForClient();
        Cargo cargo = createCargo();
        invoice.setId(1L);
        Client client = createClient();
        invoice.setNettoValue(BigDecimal.valueOf(1000));
        invoice.setCargo(cargo);
        cargo.setClient(client);

        when(invoiceClientRepository.findById(invoice.getId())).thenReturn(Optional.of(invoice));
        when(invoiceClientRepository.save(invoice)).thenReturn(invoice);
        when(clientRepository.save(client)).thenReturn(client);

        invoiceClientService.payInvoice(invoice.getId());

        assertTrue(invoice.isPaid());
        verify(invoiceClientRepository).save(invoice);
        BigDecimal expectedBalance = BigDecimal.valueOf(4000);
        assertEquals(expectedBalance, client.getBalance());
        verify(clientRepository).save(client);


    }

    private Cargo createCargo() {
        Cargo cargo = new Cargo();
        cargo.setId(1L);
        return cargo;
    }

    private InvoiceClientDto createInvoiceClientDto() {
        InvoiceClientDto dto = new InvoiceClientDto();
        return dto;
    }


    private InvoiceForClient createInvoiceForClient() {
        InvoiceForClient invoice = new InvoiceForClient();
        invoice.setPaid(false);
        return invoice;
    }


    private Order createOrder() {
        Order order = new Order();
        return order;
    }


    private Client createClient() {
        Client client = new Client();
        client.setId(1L);
        client.setBalance(BigDecimal.valueOf(5000));
        return client;
    }

    private List<InvoiceForClient> createListInvoices() {
        List<InvoiceForClient> invoices = new ArrayList<>();
        InvoiceForClient invoice1 = new InvoiceForClient();
        InvoiceForClient invoice2 = new InvoiceForClient();
        InvoiceForClient invoice3 = new InvoiceForClient();
        InvoiceForClient invoice4 = new InvoiceForClient();

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