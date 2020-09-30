package com.epam.microservices.shop.service;

import com.epam.microservices.shop.model.dto.ShopTO;

public interface ShopService {

    ShopTO findShop(String externalShopName);
}
