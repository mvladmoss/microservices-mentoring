package com.epam.microservices.shop.service.converter;

import com.epam.microservices.shop.model.dto.AddressTO;
import com.epam.microservices.shop.model.dto.CreditCardTO;
import com.epam.microservices.shop.model.dto.CustomerTO;
import com.epam.microservices.shop.model.entity.Customer;

public class CustomerConverter {

    public static CustomerTO convert(Customer customer) {
        CreditCardTO creditCardTO = CreditCardConverter.convert(customer.getCreditCard());
        AddressTO addressTO = AddressConverter.convert(customer.getAddress());

        return CustomerTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .creditCard(creditCardTO)
                .address(addressTO)
                .build();
    }
}
