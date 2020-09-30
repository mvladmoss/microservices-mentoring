package com.epam.shop.service.impl;

import org.springframework.stereotype.Service;
import com.epam.shop.service.PaymentRemoteIntegrationService;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaypalIntegrationServiceImpl implements PaymentRemoteIntegrationService {

    @Override
    public String authorize(BigDecimal totalAmount, String cardNumber, String expirationDate, String csvCode) {
        return UUID.randomUUID().toString();
    }

    @Override
    public void capture(String transactionNumber) {

    }
}
