package com.epam.microservices.shop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailsTO {

    private String orderIdentifier;
    private Long externalShopId;
    private Set<ProductTO> products;
    private String address;
    private Instant completionDate;
}
