package com.epam.microservices.shop.repository;

import com.epam.microservices.shop.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
