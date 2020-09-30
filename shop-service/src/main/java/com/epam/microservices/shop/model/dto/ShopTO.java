package com.epam.microservices.shop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShopTO {

    private Long id;
    private String externalShopName;
    private Instant created;
    private String address;
}
