package com.tslcompany.details;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class AddressDto {
    private Long id;
    private String city;
    private String postalCode;
    private String street;
    private String homeNo;
    private String flatNo;
}
