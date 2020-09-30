package com.epam.microservices.shop.service.converter;

import com.epam.microservices.shop.model.dto.IncomingLogisticalOrderDto;
import com.epam.microservices.shop.model.dto.IncomingLogisticalOrderLineDto;
import com.epam.microservices.shop.model.entity.LogisticalOrder;
import com.epam.microservices.shop.model.entity.LogisticalOrderLine;
import com.epam.microservices.shop.model.entity.OrderLogicStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class IncomingEventConverter {

    public static LogisticalOrder convert(IncomingLogisticalOrderDto logisticalOrderDto) {
        BigDecimal totalAmount = logisticalOrderDto.getOrderLines()
                .stream()
                .map(IncomingLogisticalOrderLineDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Instant now = Instant.now();
        return LogisticalOrder.builder()
                .totalAmount(totalAmount)
                .externalCustomerId(logisticalOrderDto.getExternalCustomerId())
                .externalShopId(logisticalOrderDto.getExternalShopId())
                .orderIdentifier(UUID.randomUUID().toString())
                .logicStatus(OrderLogicStatus.WAITING_VERIFICATION)
                .deliveryAddress(logisticalOrderDto.getDeliveryAddress())
                .created(now)
                .modified(now)
                .build();
    }

    public static LogisticalOrderLine convert(IncomingLogisticalOrderLineDto logisticalOrderLineDto) {
        return LogisticalOrderLine.builder()
                .productSku(logisticalOrderLineDto.getProductSku())
                .quantity(logisticalOrderLineDto.getQuantity())
                .price(logisticalOrderLineDto.getPrice())
                .build();
    }
}
