package com.epam.microservices.shop.repository;

import com.epam.microservices.shop.model.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    @Query("SELECT cr FROM CreditCard cr WHERE cr.customer.id = :customerId")
    Optional<CreditCard> findByCustomerId(@Param("customerId") Long customerId);
}
