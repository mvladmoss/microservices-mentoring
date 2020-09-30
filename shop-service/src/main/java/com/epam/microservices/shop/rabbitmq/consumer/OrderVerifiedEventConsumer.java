package com.epam.microservices.shop.rabbitmq.consumer;

import com.epam.microservices.shop.event.incoming.OrderVerifiedEvent;
import com.epam.microservices.shop.service.LogisticalOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderVerifiedEventConsumer {

    private final LogisticalOrderService logisticalOrderService;

    @RabbitListener(queues = "${rabbitmq.queues.inbound.orderVerifiedQueue}")
    public void onMessage(OrderVerifiedEvent orderVerifiedEvent) {
        logisticalOrderService.confirmOrder(orderVerifiedEvent.getOrderIdentifier());
    }
}
