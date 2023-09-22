package com.tslcompany.customer.carrier;

import com.tslcompany.customer.client.Client;
import com.tslcompany.customer.client.ClientDto;
import org.springframework.stereotype.Service;

@Service
public class CarrierMapper {

    public CarrierDto map(Carrier carrier) {
        CarrierDto dto = new CarrierDto();
        dto.setFullName(carrier.getFullName());
        dto.setShortName(carrier.getShortName());
        dto.setVatNumber(carrier.getVatNumber());
        dto.setDescription(carrier.getDescription());
        dto.setTermOfPayment(carrier.getTermOfPayment());
        return dto;
    }
    public Carrier map(CarrierDto carrierDto) {
        Carrier carrier = new Carrier();
        carrier.setFullName(carrierDto.getFullName());
        carrier.setShortName(carrierDto.getShortName());
        carrier.setVatNumber(carrierDto.getVatNumber());
        carrier.setDescription(carrierDto.getDescription());
        carrier.setTermOfPayment(carrierDto.getTermOfPayment());
        return carrier;
    }
}
