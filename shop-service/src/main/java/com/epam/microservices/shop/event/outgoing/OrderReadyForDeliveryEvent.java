package com.epam.microservices.shop.event.outgoing;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderReadyForDeliveryEvent {

    private String orderIdentifier;
    private String shopAddress;
}
