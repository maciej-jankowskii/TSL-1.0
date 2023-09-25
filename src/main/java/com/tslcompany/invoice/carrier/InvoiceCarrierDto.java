package com.tslcompany.invoice.carrier;

import com.tslcompany.order.Order;
import com.tslcompany.order.OrderDto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class InvoiceCarrierDto {

    private String invoiceNumber;
    private Long orderNumber;
    private Long carrierId;
    private BigDecimal nettoValue;
    private BigDecimal bruttoValue;
}
