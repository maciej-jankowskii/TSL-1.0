package com.tslcompany.customer.carrier;

import com.tslcompany.details.Address;
import com.tslcompany.details.AddressDto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarrierDto {
    private String fullName;
    private String shortName;
    private AddressDto address;
    private String vatNumber;
    private String description;
    private Integer termOfPayment;
}
