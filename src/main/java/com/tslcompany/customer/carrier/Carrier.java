package com.tslcompany.customer.carrier;

import com.tslcompany.details.Address;
import com.tslcompany.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Carrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String shortName;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    private String vatNumber;
    private String description;
    private Integer termOfPayment;
    private BigDecimal balance;
    @OneToMany(mappedBy = "carrier")
    private List<Order> orders = new ArrayList<>();
}
