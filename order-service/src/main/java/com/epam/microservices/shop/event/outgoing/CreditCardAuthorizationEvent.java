package com.epam.microservices.shop.event.outgoing;

import com.epam.microservices.shop.event.dto.CreditCardAuthorizationEventTO;
import com.epam.microservices.shop.model.entity.LogisticalOrder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreditCardAuthorizationEvent {

    private final CreditCardAuthorizationEventTO creditCardAuthorizationEventTO;

    public static CreditCardAuthorizationEvent of(CreditCardAuthorizationEventTO creditCardAuthorizationEventTO) {
        return new CreditCardAuthorizationEvent(creditCardAuthorizationEventTO);
    }
}
