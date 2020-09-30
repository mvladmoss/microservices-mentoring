package com.epam.microservices.shop.repository;

import com.epam.microservices.shop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.productSku = :productSku AND p.shop.id = :shopId")
    Optional<Product> findByProductSkuAndShopId(@Param("productSku") String productSku, @Param("shopId") Long shopId);
}
