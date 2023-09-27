package com.tslcompany.invoice.client;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.cargo.CargoRepository;
import com.tslcompany.customer.client.Client;
import com.tslcompany.customer.client.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class InvoiceClientService {

    private final InvoiceClientRepository invoiceClientRepository;
    private final InvoiceClientMapper invoiceClientMapper;
    private final CargoRepository cargoRepository;
    private final ClientRepository clientRepository;

    public InvoiceClientService(InvoiceClientRepository invoiceClientRepository, InvoiceClientMapper invoiceClientMapper, CargoRepository cargoRepository, ClientRepository clientRepository) {
        this.invoiceClientRepository = invoiceClientRepository;
        this.invoiceClientMapper = invoiceClientMapper;
        this.cargoRepository = cargoRepository;
        this.clientRepository = clientRepository;
    }

    public List<InvoiceForClient> findAllInvoices() {
        return (List<InvoiceForClient>) invoiceClientRepository.findAll();
    }

    public List<InvoiceForClient> filterInvoices(Boolean isPaid){
        List<InvoiceForClient> allInvoices = (List<InvoiceForClient>) invoiceClientRepository.findAll();
        if (isPaid){
            return allInvoices.stream().filter(InvoiceForClient::isPaid).toList();
        } else {
            return allInvoices.stream().filter(invoice -> !invoice.isPaid()).toList();
        }
    }

    @Transactional
    public InvoiceClientDto addInvoiceForClient(InvoiceClientDto invoiceDto) {
        InvoiceForClient invoice = invoiceClientMapper.map(invoiceDto);
        LocalDate currentDate = LocalDate.now();
        invoice.setInvoiceDate(currentDate);
        Integer termOfPayment = invoice.getCargo().getClient().getTermOfPayment();
        invoice.setDueDate(currentDate.plusDays(termOfPayment));
        invoiceClientRepository.save(invoice);

        Long cargoId = invoice.getCargo().getId();
        Cargo cargo = cargoRepository.findById(cargoId).orElseThrow(() -> new NoSuchElementException("Brak ładunku"));
        cargo.setInvoicedForClient(true);
        cargoRepository.save(cargo);

        InvoiceForClient saved = invoiceClientRepository.save(invoice);
        return invoiceClientMapper.map(saved);
    }
    @Transactional
    public void payInvoice(Long invoiceId){
        InvoiceForClient invoice = invoiceClientRepository.findById(invoiceId).orElseThrow(() -> new NoSuchElementException("Brak faktury"));

        if (invoice.isPaid()){
            throw new IllegalStateException("Faktura już opłacona");
        } else {
            invoice.setPaid(true);
            Client client = invoice.getCargo().getClient();
            BigDecimal value = invoice.getNettoValue();
            BigDecimal newBalance = client.getBalance().subtract(value);
            client.setBalance(newBalance);
            invoiceClientRepository.save(invoice);
            clientRepository.save(client);
        }

    }
}
