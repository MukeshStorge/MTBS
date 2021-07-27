package com.moviebooking.paymentgateway;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class PaymentGatewayCall {

    public boolean payment(double amount) {
        return new Random().nextBoolean();
    }

}
