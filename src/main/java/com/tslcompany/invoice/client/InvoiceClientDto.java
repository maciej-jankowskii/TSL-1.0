package com.tslcompany.invoice.client;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class InvoiceClientDto {

    private String invoiceNumber;
    private Long cargoId;
    private BigDecimal nettoValue;
    private BigDecimal bruttoValue;
    private boolean isPaid;
}
