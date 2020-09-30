package com.epam.microservices.shop.service;

import com.epam.microservices.shop.event.incoming.OrderCreatedEvent;
import com.epam.microservices.shop.model.dto.OrderDetailsTO;

import java.time.Instant;
import java.util.List;

public interface LogisticalOrderService {

    void createOrder(OrderCreatedEvent orderCreatedEvent);

    List<OrderDetailsTO> findWaitingAcceptanceOrders(Long shopId);

    void acceptOrder(String orderIdentifier, Instant completionDate);

    void confirmOrder(String orderIdentifier);

    void completeOrder(String orderIdentifier, Instant completionDate);
}
