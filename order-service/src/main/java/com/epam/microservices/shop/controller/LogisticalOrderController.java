package com.epam.microservices.shop.controller;

import com.epam.microservices.shop.model.dto.IncomingLogisticalOrderDto;
import com.epam.microservices.shop.model.dto.OrderDetailsTO;
import com.epam.microservices.shop.service.LogisticalOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class LogisticalOrderController {

    private final LogisticalOrderService logisticalOrderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody IncomingLogisticalOrderDto logisticalOrderDto) {
        return logisticalOrderService.createOrder(logisticalOrderDto);
    }

    @GetMapping("/{orderIdentifier}")
    public OrderDetailsTO getOrderDetails(@PathVariable String orderIdentifier) {
        return logisticalOrderService.getOrderDetails(orderIdentifier);
    }

    @GetMapping("/{orderIdentifier}/status")
    public String getOrderStatus(@PathVariable String orderIdentifier) {
        return logisticalOrderService.getOrderStatus(orderIdentifier);
    }
}
