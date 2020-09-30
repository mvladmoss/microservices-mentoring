package com.epam.microservices.shop.event.dto;

import com.epam.microservices.shop.model.dto.OrderLineDetailsTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEventTO {

    private String orderIdentifier;
    private Long externalShopId;
    private Set<OrderLineDetailsTO> orderLines;
    private String address;
    private Long customerId;
}
