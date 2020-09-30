package com.epam.microservices.shop.service.converter;

import com.epam.microservices.shop.model.dto.AddressTO;
import com.epam.microservices.shop.model.entity.Address;

public class AddressConverter {

    public static AddressTO convert(Address address) {
        return AddressTO.builder()
                .city(address.getCity())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .phoneNumber(address.getPhoneNumber())
                .build();
    }
}
