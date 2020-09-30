package com.epam.shop.repository;

import com.epam.shop.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByTransactionNumber(String transactionNumber);

    Optional<Payment> findByOrderIdentifier(String orderIdentifier);

}
