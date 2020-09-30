package com.epam.shop.service;

import java.math.BigDecimal;

public interface PaymentRemoteIntegrationService {

    String authorize(BigDecimal totalAmount, String cardNumber, String expirationDate, String csvCode);

    void capture(String transactionNumber);
}
