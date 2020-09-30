package com.epam.microservices.shop.service.converter;

import com.epam.microservices.shop.event.outgoing.OrderPreparedEvent;
import com.epam.microservices.shop.event.outgoing.OrderReadyForDeliveryEvent;
import com.epam.microservices.shop.model.entity.LogisticalOrder;
import com.epam.microservices.shop.model.entity.Shop;

public class OutgoingEventConverter {

    public static OrderPreparedEvent convert(LogisticalOrder logisticalOrder) {
        return OrderPreparedEvent.builder()
                .orderIdentifier(logisticalOrder.getExternalOrderIdentifier())
                .build();
    }

    public static OrderReadyForDeliveryEvent convert(LogisticalOrder logisticalOrder, Shop shop) {
        return OrderReadyForDeliveryEvent.builder()
                .orderIdentifier(logisticalOrder.getExternalOrderIdentifier())
                .shopAddress(shop.getAddress())
                .build();
    }
}
