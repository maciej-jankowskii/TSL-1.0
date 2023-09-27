package com.tslcompany.forwarder;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ForwarderDto {

    private Long userId;
    private String email;
    private BigDecimal totalMargin;
}
