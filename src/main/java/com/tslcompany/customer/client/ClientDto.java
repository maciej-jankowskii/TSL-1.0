package com.tslcompany.customer.client;


import com.tslcompany.details.AddressDto;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ClientDto {
    private Long id;
    private String fullName;
    private String shortName;
    private AddressDto address;
    private String vatNumber;
    private String description;
    private Integer termOfPayment;

}
