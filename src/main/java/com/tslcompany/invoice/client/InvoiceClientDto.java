package com.tslcompany.invoice.client;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InvoiceClientDto {

    private String invoiceNumber;
    private LocalDate invoiceDate;
    private LocalDate dueDate;
    private Long cargoId;
    private BigDecimal nettoValue;
    private BigDecimal bruttoValue;
    private boolean isPaid;
}
