package com.epam.microservices.shop.service;

public interface CustomerVerificationService {

    void verify(Long customerId, String orderIdentifier);

}
