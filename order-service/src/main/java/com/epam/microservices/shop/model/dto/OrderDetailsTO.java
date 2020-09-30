package com.epam.microservices.shop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsTO {

    private String orderIdentifier;
    private Long externalShopId;
    private String orderStatus;
    private BigDecimal totalAmount;
    private String deliveryAddress;
    private Long orderCompletionDate;
    private Long orderDeliveryDate;
    private Set<OrderLineDetailsTO> orderLineDetails;
}
