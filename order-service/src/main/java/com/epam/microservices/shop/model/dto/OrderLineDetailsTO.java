package com.epam.microservices.shop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineDetailsTO implements Serializable {

    private String productSku;
    private Integer quantity;
    private BigDecimal price;
}
