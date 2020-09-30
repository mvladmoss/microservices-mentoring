package com.epam.microservices.shop.service;

import com.epam.microservices.shop.model.dto.CreditCardTO;
import com.epam.microservices.shop.model.entity.CreditCard;

public interface CreditCardService {

    CreditCard createCreditCard(CreditCardTO creditCardTO);

    CreditCardTO findCreditCardDetails(Long customerId);

}
