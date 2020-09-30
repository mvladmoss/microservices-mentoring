package com.epam.microservices.shop.repository;

import com.epam.microservices.shop.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByLogin(String login);
}
