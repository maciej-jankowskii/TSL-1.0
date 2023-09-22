package com.tslcompany.order;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.details.OrderStatus;
import com.tslcompany.truck.Truck;
import jakarta.persistence.*;
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
