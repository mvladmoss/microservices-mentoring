package com.epam.microservices.shop.event.outgoing;

import com.epam.microservices.shop.model.entity.LogisticalOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreditCardCaptureEvent {

    private final LogisticalOrder logOrder;

    public static CreditCardCaptureEvent of(LogisticalOrder logOrder) {
        return new CreditCardCaptureEvent(logOrder);
    }
}
