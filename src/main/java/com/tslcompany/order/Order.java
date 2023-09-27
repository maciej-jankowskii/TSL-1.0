package com.tslcompany.order;

import com.tslcompany.cargo.Cargo;
import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.details.OrderStatus;
import com.tslcompany.truck.Truck;
import com.tslcompany.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_to_send")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate dateAdded;
    @OneToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;
    @ManyToOne
    @JoinColumn(name = "carrier_id")
    private Carrier carrier;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Truck typeOfTruck;
    private String truckNumbers;
    @Enumerated(EnumType.STRING)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private BigDecimal margin;
    private OrderStatus orderStatus;
    private boolean isInvoiced;
}
