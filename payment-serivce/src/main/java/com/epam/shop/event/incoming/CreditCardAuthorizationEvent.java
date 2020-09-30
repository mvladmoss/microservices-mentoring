package com.epam.shop.event.incoming;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardAuthorizationEvent {

    private Long externalShopId;
    private Long externalCustomerId;
    private BigDecimal totalAmount;
    private String orderIdentifier;
}
