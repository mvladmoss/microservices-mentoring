package com.epam.microservices.shop.event.incoming;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreditCardAuthorizedEvent {

    private String orderIdentifier;
    private Long externalPaymentId;
    private String transactionId;
}
