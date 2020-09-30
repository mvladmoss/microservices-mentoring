package com.epam.microservices.shop.event.outgoing;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderPreparedEvent {

    private String orderIdentifier;
}
