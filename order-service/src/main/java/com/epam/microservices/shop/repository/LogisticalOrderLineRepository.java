package com.epam.microservices.shop.repository;

import com.epam.microservices.shop.model.entity.LogisticalOrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogisticalOrderLineRepository extends JpaRepository<LogisticalOrderLine, Long> {
}
