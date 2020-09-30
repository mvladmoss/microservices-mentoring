package com.epam.microservices.shop.service.converter;

import com.epam.microservices.shop.model.dto.OrderDetailsTO;
import com.epam.microservices.shop.model.dto.ProductTO;
import com.epam.microservices.shop.model.entity.LogisticalOrder;

import java.util.Set;
import java.util.stream.Collectors;

public class LogisticalOrderConverter {

    public static OrderDetailsTO convert(LogisticalOrder order) {
        Set<ProductTO> products = order.getProducts().stream()
                .map(productOrder -> ProductTO.builder()
                        .productSku(productOrder.getProduct().getProductSku())
                        .quantity(productOrder.getQuantity())
                        .build())
                .collect(Collectors.toSet());
        return OrderDetailsTO.builder()
                .orderIdentifier(order.getExternalOrderIdentifier())
                .completionDate(order.getCompletionDate())
                .products(products)
                .build();
    }
}
