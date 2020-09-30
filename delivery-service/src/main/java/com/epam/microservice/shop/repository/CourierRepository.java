package com.epam.microservice.shop.repository;

import com.epam.microservice.shop.model.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}
