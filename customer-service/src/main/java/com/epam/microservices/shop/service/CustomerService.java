package com.epam.microservices.shop.service;

import com.epam.microservices.shop.model.dto.CustomerTO;

public interface CustomerService {

    void createCustomer(CustomerTO customerTO);

    CustomerTO findCustomer(Long id);

    CustomerTO findCustomerByLogin(String login);

    void deleteCustomer(Long id);

    void deactivateCustomer(Long id);
}
