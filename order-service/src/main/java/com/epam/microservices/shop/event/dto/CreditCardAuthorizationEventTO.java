package com.epam.microservices.shop.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardAuthorizationEventTO {

    private Long externalShopId;
    private Long externalCustomerId;
    private BigDecimal totalAmount;
    private String orderIdentifier;
}
