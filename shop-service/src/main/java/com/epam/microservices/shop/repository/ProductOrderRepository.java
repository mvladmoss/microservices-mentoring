package com.epam.microservices.shop.repository;

import com.epam.microservices.shop.model.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {
}
