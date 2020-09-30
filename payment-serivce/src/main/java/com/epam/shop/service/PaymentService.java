package com.epam.shop.service;

import com.epam.shop.event.incoming.CreditCardAuthorizationEvent;

public interface PaymentService {

    void authorize(CreditCardAuthorizationEvent creditCardAuthorizationEvent);

    void capture(String transactionNumber);

    String findPaymentStatus(String orderIdentifier);
}
