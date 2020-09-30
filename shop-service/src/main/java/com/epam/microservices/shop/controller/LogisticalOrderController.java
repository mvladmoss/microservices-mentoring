package com.epam.microservices.shop.controller;

import com.epam.microservices.shop.model.dto.OrderAcceptanceTO;
import com.epam.microservices.shop.model.dto.OrderCompletionTO;
import com.epam.microservices.shop.model.dto.OrderDetailsTO;
import com.epam.microservices.shop.service.LogisticalOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class LogisticalOrderController {

    private final LogisticalOrderService logisticalOrderService;

    @GetMapping("/{shopId}")
    public List<OrderDetailsTO> getWaitingAcceptanceOrders(@PathVariable Long shopId) {
        return logisticalOrderService.findWaitingAcceptanceOrders(shopId);
    }

    @PostMapping("/{orderIdentifier}")
    public void acceptOrder(@PathVariable String orderIdentifier, @RequestBody OrderCompletionTO orderCompletionTO) {
        logisticalOrderService.acceptOrder(orderIdentifier, Instant.ofEpochSecond(orderCompletionTO.getCompletionDate()));
    }

    @PostMapping
    public void acceptOrders(@RequestBody List<OrderAcceptanceTO> orderAcceptanceTOS) {
        orderAcceptanceTOS.forEach(orderAcceptanceTO -> logisticalOrderService.acceptOrder(orderAcceptanceTO.getOrderIdentifier(),
                Instant.ofEpochSecond(orderAcceptanceTO.getCompletionDate())));
    }

    @PostMapping("/{orderIdentifier}/completion")
    public void confirmOrder(@PathVariable String orderIdentifier, @RequestBody OrderCompletionTO orderCompletionTO) {
        logisticalOrderService.completeOrder(orderIdentifier, Instant.ofEpochSecond(orderCompletionTO.getCompletionDate()));
    }
}
