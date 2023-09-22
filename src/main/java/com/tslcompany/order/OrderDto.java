package com.tslcompany.order;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class OrderDto {

    private Long cargoId;
    private Long carrierId;
    private BigDecimal price;
    private String typeOfTruck;
    private String truckNumbers;
    private String orderStatus;
}
