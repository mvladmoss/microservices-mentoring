package com.epam.microservices.shop.repository;

import com.epam.microservices.shop.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {

}
