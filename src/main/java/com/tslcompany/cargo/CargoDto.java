package com.tslcompany.cargo;


import com.tslcompany.customer.client.ClientDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CargoDto {

    private String cargoNumberFromCustomer;
    private BigDecimal price;
    private ClientDto clientDto;
    private LocalDate loadingDate;
    private LocalDate unloadingDate;
    private String loadingAddress;
    private String unloadingAddress;
    private String goods;
    private String description;
    private boolean assignedToOrder;
}
