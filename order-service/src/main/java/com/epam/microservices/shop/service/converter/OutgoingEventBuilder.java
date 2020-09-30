package com.epam.microservices.shop.service.converter;

import com.epam.microservices.shop.event.dto.CreditCardAuthorizationEventTO;
import com.epam.microservices.shop.event.dto.OrderCreatedEventTO;
import com.epam.microservices.shop.model.dto.OrderLineDetailsTO;
import com.epam.microservices.shop.model.entity.LogisticalOrder;
import sun.rmi.runtime.Log;

import java.util.Set;
import java.util.stream.Collectors;

public class OutgoingEventBuilder {

    public static OrderCreatedEventTO buildOrderCreatedEventTO(LogisticalOrder logOrder) {
        Set<OrderLineDetailsTO> orderLineDetailsTOs = logOrder.getOrderLines().stream()
                .map(orderLine -> OrderLineDetailsTO.builder()
                        .productSku(orderLine.getProductSku())
                        .price(orderLine.getPrice())
                        .quantity(orderLine.getQuantity())
                        .build())
                .collect(Collectors.toSet());

        return OrderCreatedEventTO.builder()
                .orderIdentifier(logOrder.getOrderIdentifier())
                .externalShopId(logOrder.getExternalShopId())
                .customerId(logOrder.getExternalCustomerId())
                .address(logOrder.getDeliveryAddress())
                .orderLines(orderLineDetailsTOs)
                .build();
    }

    public static CreditCardAuthorizationEventTO buildCreditCardAuthorizationEventTO(LogisticalOrder logOrder) {
        return CreditCardAuthorizationEventTO.builder()
                .externalCustomerId(logOrder.getExternalCustomerId())
                .orderIdentifier(logOrder.getOrderIdentifier())
                .externalShopId(logOrder.getExternalShopId())
                .totalAmount(logOrder.getTotalAmount())
                .build();
    }
}
