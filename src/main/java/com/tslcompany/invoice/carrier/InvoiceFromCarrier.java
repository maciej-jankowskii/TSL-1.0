package com.tslcompany.invoice.carrier;

import com.tslcompany.customer.carrier.Carrier;
import com.tslcompany.order.Order;
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
public class InvoiceFromCarrier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name="carrier_id")
    private Carrier carrier;
    private BigDecimal nettoValue;
    private BigDecimal bruttoValue;
    private boolean isPaid;
}
