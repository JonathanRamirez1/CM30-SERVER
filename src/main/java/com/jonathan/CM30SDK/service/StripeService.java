package com.jonathan.CM30SDK.service;

import com.jonathan.CM30SDK.model.CardData;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Token;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    @Value("${stripe.apiKey}")
    private String apiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }

    public String createStripeToken(CardData cardData) throws StripeException {
        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", cardData.getNumber());
        cardParams.put("exp_month", cardData.getExpMonth());
        cardParams.put("exp_year", cardData.getExpYear());
        cardParams.put("cvc", cardData.getCvc());
        Map<String, Object> tokenParams = new HashMap<>();
        tokenParams.put("card", cardParams);

        Token token = Token.create(tokenParams);
        return token.getId();
    }

    public String createPaymentIntent(Long amount, String currency) throws StripeException {
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(amount)
                .setCurrency(currency)
                .setPaymentMethod("pm_card_visa")
                .build();

        PaymentIntent intent = PaymentIntent.create(params);
        return intent.getClientSecret();
    }
}
