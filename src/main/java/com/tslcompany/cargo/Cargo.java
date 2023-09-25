package com.tslcompany.cargo;

import com.tslcompany.customer.client.Client;
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
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cargoNumberFromCustomer;
    private BigDecimal price;
    private LocalDate dateAdded;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    private LocalDate loadingDate;
    private LocalDate unloadingDate;
    private String loadingAddress;
    private String unloadingAddress;
    private String goods;
    private String description;
    private boolean assignedToOrder;
    private boolean invoicedForClient;
}
