package com.epam.microservices.shop.repository;

import com.epam.microservices.shop.model.entity.LogisticalOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LogisticalOrderRepository extends JpaRepository<LogisticalOrder, Long> {

    @Query(nativeQuery = true, value = "" +
            "SELECT lo " +
            "FROM logistical_order lo " +
            "   JOIN product_order po ON lo.id = po.logistical_order_id " +
            "   JOIN product p ON p.id = po.product_id " +
            "WHERE p.shop_id = :shopId " +
            "   AND lo.status = :status")
    List<LogisticalOrder> findByExternalShopIdAndStatus(Long shopId, String status);

    Optional<LogisticalOrder> findByExternalOrderIdentifier(String orderIdentifier);
}
