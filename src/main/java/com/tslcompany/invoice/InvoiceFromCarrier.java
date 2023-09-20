package com.tslcompany.invoice;

import com.tslcompany.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class InvoiceFromCarrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoiceNumber;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private BigDecimal nettoValue;
    private BigDecimal bruttoValue;
}
