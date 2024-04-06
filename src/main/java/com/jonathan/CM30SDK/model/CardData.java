package com.jonathan.CM30SDK.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardData {
    private String number;
    private int expMonth;
    private int expYear;
    private String cvc;
}
