package com.epam.microservices.shop.repository;

import com.epam.microservices.shop.model.entity.LogisticalOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface LogisticalOrderRepository extends JpaRepository<LogisticalOrder, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT log_order FROM LogisticalOrder log_order where orderIdentifier = :orderIdentifier")
    LogisticalOrder findByOrderIdentifierAndLock(@Param("orderIdentifier") String orderIdentifier);

    Optional<LogisticalOrder> findByOrderIdentifier(String orderIdentifier);

}
