package com.epam.microservice.shop.repository;

import com.epam.microservice.shop.model.entity.LogisticalOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogisticalOrderRepository extends JpaRepository<LogisticalOrder, Long> {
}
