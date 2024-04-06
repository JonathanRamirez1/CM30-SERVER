package com.jonathan.CM30SDK.model;

import lombok.Getter;

@Getter
public class PaymentResponse {

    private String clientSecret;

    public PaymentResponse(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
