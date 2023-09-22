package com.tslcompany.cargo;

import com.tslcompany.customer.client.Client;
import com.tslcompany.customer.client.ClientDto;
import com.tslcompany.details.Address;
import com.tslcompany.details.AddressDto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class CargoDto {


    private String cargoNumberFromCustomer;
    private BigDecimal price;
    private LocalDate dateAdded;
    private ClientDto clientDto;
    private LocalDate loadingDate;
    private LocalDate unloadingDate;
    private String loadingAddress;
    private String unloadingAddress;
    private String goods;
    private String description;
}
