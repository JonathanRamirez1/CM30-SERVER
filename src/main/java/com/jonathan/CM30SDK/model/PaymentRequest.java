package com.jonathan.CM30SDK.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {

    private Long amount;
    private String currency;
}
