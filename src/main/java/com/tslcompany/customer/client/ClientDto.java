package com.tslcompany.customer.client;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.details.Address;
import com.tslcompany.details.AddressDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClientDto {

    private String fullName;
    private String shortName;
    private AddressDto address;
    private String vatNumber;
    private String description;
    private Integer termOfPayment;

}
