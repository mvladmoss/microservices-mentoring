package com.epam.microservices.shop.service.impl;

import com.epam.microservices.shop.model.dto.CreditCardTO;
import com.epam.microservices.shop.model.entity.CreditCard;
import com.epam.microservices.shop.repository.CreditCardRepository;
import com.epam.microservices.shop.service.CreditCardService;
import com.epam.microservices.shop.service.converter.CreditCardConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;

    @Override
    @Transactional
    public CreditCard createCreditCard(CreditCardTO creditCardTO) {
        CreditCard creditCard = CreditCard.builder()
                .bankProvider(creditCardTO.getBankProvider())
                .cardNumber(creditCardTO.getCardNumber())
                .expirationDate(creditCardTO.getExpirationDate())
                .cvsCode(creditCardTO.getCvsCode())
                .build();
        return creditCardRepository.save(creditCard);
    }

    @Override
    public CreditCardTO findCreditCardDetails(Long customerId) {
        return creditCardRepository.findByCustomerId(customerId)
                .map(CreditCardConverter::convert)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Customer %s doesn't exist", customerId)));
    }


}
