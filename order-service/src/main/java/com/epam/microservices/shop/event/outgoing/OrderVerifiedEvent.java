package com.epam.microservices.shop.event.outgoing;

import com.epam.microservices.shop.event.dto.OrderVerifiedEventTO;
import com.epam.microservices.shop.model.entity.LogisticalOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderVerifiedEvent {

    private final OrderVerifiedEventTO orderVerifiedEventTO;

    public static OrderVerifiedEvent of(OrderVerifiedEventTO orderVerifiedEventTO) {
        return new OrderVerifiedEvent(orderVerifiedEventTO);
    }
}
