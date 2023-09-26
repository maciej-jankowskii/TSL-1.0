package com.tslcompany.invoice.client;

import com.tslcompany.cargo.Cargo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class InvoiceForClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoiceNumber;
    @OneToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;
    private BigDecimal nettoValue;
    private BigDecimal bruttoValue;
    private boolean isPaid;

}
