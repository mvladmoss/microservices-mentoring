//package com.epam.microservices.shop.controller;
//
//import com.epam.microservices.shop.model.dto.ShopTO;
//import com.epam.microservices.shop.service.ShopService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/shops")
//@RequiredArgsConstructor
//public class ShopController {
//
////    private final ShopService shopService;
//
//    @GetMapping("/{externalShopName}")
//    public ShopTO getShop(@PathVariable String externalShopName) {
//        return shopService.findShop(externalShopName);
//    }
//}
