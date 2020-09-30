package com.epam.microservices.shop.service;

import com.epam.microservices.shop.model.entity.LogisticalOrder;

public interface PaymentService {

    void authorizeCreditCard(LogisticalOrder logisticalOrder);
}
