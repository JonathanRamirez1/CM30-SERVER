package com.jonathan.CM30SDK.controller;

import com.jonathan.CM30SDK.model.CardData;
import com.jonathan.CM30SDK.model.PaymentRequest;
import com.jonathan.CM30SDK.model.PaymentResponse;
import com.jonathan.CM30SDK.model.TokenRequest;
import com.jonathan.CM30SDK.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final StripeService stripeService;


    @Autowired
    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/create-token")
    public ResponseEntity<Map<String, String>> createToken(@RequestBody CardData cardData) {
        try {
            String token = stripeService.createStripeToken(cardData);
            Map<String, String> tokenResponse = Collections.singletonMap("token", token);
            return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
        } catch (StripeException e) {
            return new ResponseEntity<>(Collections.singletonMap("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-payment-method")
    public ResponseEntity<?> createPaymentMethodFromToken(@RequestBody TokenRequest tokenRequest) {
        try {
            PaymentMethod paymentMethod = PaymentMethod.create(
                    Map.of("type", "card", "card", Map.of("token", tokenRequest.getToken()))
            );
            Map<String, String> response = new HashMap<>();
            response.put("paymentMethodId", paymentMethod.getId());
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", e.getMessage()));
        }
    }


    @PostMapping("/create-payment-intent")
        public ResponseEntity<?> createPaymentIntent(@RequestBody PaymentRequest paymentRequest) {
            try {
                String clientSecret = stripeService.createPaymentIntent(paymentRequest.getAmount(), paymentRequest.getCurrency());
                return ResponseEntity.ok(new PaymentResponse(clientSecret));
            } catch (StripeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment intent: " + e.getMessage());
            }
        }
}
