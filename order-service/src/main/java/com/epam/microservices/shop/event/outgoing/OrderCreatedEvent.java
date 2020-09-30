package com.epam.microservices.shop.event.outgoing;

import com.epam.microservices.shop.event.dto.OrderCreatedEventTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class OrderCreatedEvent {

    private final OrderCreatedEventTO orderCreatedEventTO;

    public static OrderCreatedEvent of(OrderCreatedEventTO orderCreatedEventTO) {
        return new OrderCreatedEvent(orderCreatedEventTO);
    }
}
