package com.tslcompany.customer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class ClientMapper {

    public ClientDto map(Client client) {
        ClientDto dto = new ClientDto();
        dto.setFullName(client.getFullName());
        dto.setShortName(client.getShortName());
        dto.setVatNumber(client.getVatNumber());
        dto.setDescription(client.getDescription());
        dto.setTermOfPayment(client.getTermOfPayment());
        return dto;
    }
    public Client map(ClientDto clientDto) {
        Client client = new Client();
        client.setFullName(clientDto.getFullName());
        client.setShortName(clientDto.getShortName());
        client.setVatNumber(clientDto.getVatNumber());
        client.setDescription(clientDto.getDescription());
        client.setTermOfPayment(clientDto.getTermOfPayment());
        return client;
    }
}
