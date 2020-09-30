package com.epam.microservices.shop.service.converter;

import com.epam.microservices.shop.model.dto.OrderDetailsTO;
import com.epam.microservices.shop.model.dto.OrderLineDetailsTO;
import com.epam.microservices.shop.model.entity.LogisticalOrder;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

public class LogisticalOrderConverter {

    public static OrderDetailsTO convert(LogisticalOrder logisticalOrder) {
        Set<OrderLineDetailsTO> orderLineDetailsTOs = logisticalOrder.getOrderLines().stream()
                .map(orderLine -> OrderLineDetailsTO.builder()
                        .productSku(orderLine.getProductSku())
                        .price(orderLine.getPrice())
                        .quantity(orderLine.getQuantity())
                        .build())
                .collect(Collectors.toSet());

        Instant orderCompletionDate = logisticalOrder.getOrderCompletionDate();
        Instant orderDeliveryDate = logisticalOrder.getOrderDeliveryDate();
        return OrderDetailsTO.builder()
                .orderIdentifier(logisticalOrder.getOrderIdentifier())
                .externalShopId(logisticalOrder.getExternalShopId())
                .orderStatus(logisticalOrder.getLogicStatus().name())
                .totalAmount(logisticalOrder.getTotalAmount())
                .deliveryAddress(logisticalOrder.getDeliveryAddress())
                .orderCompletionDate(orderCompletionDate == null ? null : orderCompletionDate.getEpochSecond())
                .orderDeliveryDate(orderDeliveryDate == null ? null : orderDeliveryDate.getEpochSecond())
                .orderLineDetails(orderLineDetailsTOs)
                .build();
    }
}
