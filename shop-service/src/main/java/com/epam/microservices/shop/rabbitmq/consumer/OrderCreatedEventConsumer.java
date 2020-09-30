package com.epam.microservices.shop.rabbitmq.consumer;

import com.epam.microservices.shop.event.incoming.OrderCreatedEvent;
import com.epam.microservices.shop.service.LogisticalOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreatedEventConsumer {

    private final LogisticalOrderService logisticalOrderService;

    @RabbitListener(queues = "${rabbitmq.queues.inbound.orderCreatedQueue}")
    public void onMessage(OrderCreatedEvent orderCreatedEvent) {
        logisticalOrderService.createOrder(orderCreatedEvent);
    }
}
