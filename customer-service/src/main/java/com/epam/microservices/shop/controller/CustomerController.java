package com.epam.microservices.shop.controller;

import com.epam.microservices.shop.model.dto.CreditCardTO;
import com.epam.microservices.shop.model.dto.CustomerTO;
import com.epam.microservices.shop.service.CreditCardService;
import com.epam.microservices.shop.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CreditCardService creditCardService;

    @PostMapping
    public void createCustomer(CustomerTO customerTO) {
        customerService.createCustomer(customerTO);
    }

    @GetMapping("/{id}")
    public CustomerTO findCustomer(@PathVariable Long id) {
        return customerService.findCustomer(id);
    }

    @GetMapping("/{login}")
    public CustomerTO findCustomerByLogin(@PathVariable String login) {
        return customerService.findCustomerByLogin(login);
    }

    @GetMapping("/{id}/credit_card")
    public Mono<CreditCardTO> findCustomerCreditCardDetails(@PathVariable Long id) {
        return Mono.just(creditCardService.findCreditCardDetails(id));
    }

    @PostMapping("/{id}/deactivate")
    public void deactivateCustomer(@PathVariable Long id) {
        customerService.deactivateCustomer(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }


}
