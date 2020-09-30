package com.epam.microservices.shop.service;

import com.epam.microservices.shop.model.dto.IncomingLogisticalOrderDto;
import com.epam.microservices.shop.model.dto.OrderDetailsTO;

public interface LogisticalOrderService {

    String createOrder(IncomingLogisticalOrderDto logisticalOrderDto);

    OrderDetailsTO getOrderDetails(String orderIdentifier);

    String getOrderStatus(String orderIdentifier);

}
