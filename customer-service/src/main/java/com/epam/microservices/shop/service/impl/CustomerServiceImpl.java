package com.epam.microservices.shop.service.impl;

import com.epam.microservices.shop.model.dto.CustomerTO;
import com.epam.microservices.shop.model.entity.Address;
import com.epam.microservices.shop.model.entity.CreditCard;
import com.epam.microservices.shop.model.entity.Customer;
import com.epam.microservices.shop.repository.CustomerRepository;
import com.epam.microservices.shop.service.AddressService;
import com.epam.microservices.shop.service.CreditCardService;
import com.epam.microservices.shop.service.CustomerService;
import com.epam.microservices.shop.service.converter.CustomerConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CreditCardService creditCardService;
    private final AddressService addressService;

    @Override
    @Transactional
    public void createCustomer(CustomerTO customerTO) {
        CreditCard creditCard = creditCardService.createCreditCard(customerTO.getCreditCard());
        Address address = addressService.createAddress(customerTO.getAddress());

        Customer customer = Customer.builder()
                .name(customerTO.getName())
                .surname(customerTO.getSurname())
                .login(customerTO.getLogin())
                .password(customerTO.getPassword())
                .isActive(true)
                .creditCard(creditCard)
                .address(address)
                .build();
        customerRepository.save(customer);
    }

    @Override
    @Transactional
    public CustomerTO findCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Customer with id=%s doesn't exist", id)));
        return CustomerConverter.convert(customer);
    }

    @Override
    public CustomerTO findCustomerByLogin(String login) {
        Customer customer = customerRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Customer with login=%s doesn't exist", login)));
        return CustomerConverter.convert(customer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Customer with id=%s doesn't exist", id)));
        customerRepository.delete(customer);
    }

    @Override
    @Transactional
    public void deactivateCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Customer with id=%s doesn't exist", id)));
        customer.setIsActive(false);
    }
}
