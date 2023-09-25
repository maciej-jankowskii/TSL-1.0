package com.tslcompany.invoice.client;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.cargo.CargoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class InvoiceClientService {

    private final InvoiceClientRepository invoiceClientRepository;
    private final InvoiceClientMapper invoiceClientMapper;
    private final CargoRepository cargoRepository;

    public InvoiceClientService(InvoiceClientRepository invoiceClientRepository, InvoiceClientMapper invoiceClientMapper, CargoRepository cargoRepository) {
        this.invoiceClientRepository = invoiceClientRepository;
        this.invoiceClientMapper = invoiceClientMapper;
        this.cargoRepository = cargoRepository;
    }

    public List<InvoiceForClient> findAllInvoices(){
        return (List<InvoiceForClient>) invoiceClientRepository.findAll();
    }
    @Transactional
    public InvoiceClientDto addInvoiceForClient(InvoiceClientDto invoiceDto){
        InvoiceForClient invoice = invoiceClientMapper.map(invoiceDto);
        Long cargoId = invoice.getCargo().getId();
        Cargo cargo = cargoRepository.findById(cargoId).orElseThrow(() -> new NoSuchElementException("Brak ładunku"));
        cargo.setInvoicedForClient(true);
        cargoRepository.save(cargo);

        InvoiceForClient saved = invoiceClientRepository.save(invoice);
        return invoiceClientMapper.map(saved);
    }
}
