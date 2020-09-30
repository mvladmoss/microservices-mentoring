package com.epam.microservices.shop.service;

import com.epam.microservices.shop.model.dto.AddressTO;
import com.epam.microservices.shop.model.entity.Address;

public interface AddressService {

    Address createAddress(AddressTO addressTO);

}
