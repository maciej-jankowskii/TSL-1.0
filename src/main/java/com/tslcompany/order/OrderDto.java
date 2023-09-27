package com.tslcompany.order;


import com.tslcompany.user.UserDto;
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
    private Long userId;
    private BigDecimal margin;
    private String orderStatus;
    private boolean isInvoiced;

}
