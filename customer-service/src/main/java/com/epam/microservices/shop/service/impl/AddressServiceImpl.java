package com.epam.microservices.shop.service.impl;

import com.epam.microservices.shop.model.dto.AddressTO;
import com.epam.microservices.shop.model.entity.Address;
import com.epam.microservices.shop.repository.AddressRepository;
import com.epam.microservices.shop.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public Address createAddress(AddressTO addressTO) {
        Address address = Address.builder()
                .city(addressTO.getCity())
                .street(addressTO.getStreet())
                .houseNumber(addressTO.getHouseNumber())
                .phoneNumber(addressTO.getPhoneNumber())
                .build();
        return addressRepository.save(address);
    }
}
