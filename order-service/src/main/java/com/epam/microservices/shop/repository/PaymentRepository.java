package com.epam.microservices.shop.repository;

import com.epam.microservices.shop.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
