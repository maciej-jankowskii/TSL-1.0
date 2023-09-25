package com.tslcompany.invoice.client;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.cargo.CargoRepository;
import org.springframework.stereotype.Service;

@Service
public class InvoiceClientMapper {

    private final CargoRepository cargoRepository;

    public InvoiceClientMapper(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    InvoiceClientDto map(InvoiceForClient invoice) {
        InvoiceClientDto dto = new InvoiceClientDto();
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setCargoId(invoice.getCargo().getId());
        dto.setNettoValue(invoice.getNettoValue());
        dto.setBruttoValue(invoice.getBruttoValue());
        return dto;
    }

    InvoiceForClient map(InvoiceClientDto dto) {
        InvoiceForClient invoice = new InvoiceForClient();
        invoice.setInvoiceNumber(dto.getInvoiceNumber());
        Cargo cargo = cargoRepository.findById(dto.getCargoId()).orElse(null);
        invoice.setCargo(cargo);
        invoice.setNettoValue(dto.getNettoValue());
        invoice.setBruttoValue(dto.getBruttoValue());
        return invoice;

    }
}
