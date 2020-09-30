package com.epam.microservices.shop.service.converter;

import com.epam.microservices.shop.model.dto.CreditCardTO;
import com.epam.microservices.shop.model.entity.CreditCard;

public class CreditCardConverter {

    public static CreditCardTO convert(CreditCard creditCard) {
        return CreditCardTO.builder()
                .bankProvider(creditCard.getBankProvider())
                .cardNumber(creditCard.getCardNumber())
                .expirationDate(creditCard.getExpirationDate())
                .cvsCode(creditCard.getCvsCode())
                .build();
    }

}
