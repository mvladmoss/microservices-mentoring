package com.epam.shop.event.outcoming;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CreditCardAuthorizedEvent {

    private String orderIdentifier;
    private String transactionId;
    private Long externalPaymentId;
}
