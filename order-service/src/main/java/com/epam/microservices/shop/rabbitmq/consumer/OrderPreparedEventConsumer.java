package com.epam.microservices.shop.rabbitmq.consumer;

import com.epam.microservices.shop.event.incoming.OrderPreparedEvent;
import com.epam.microservices.shop.service.orderupdate.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPreparedEventConsumer {

    private final EventHandler<OrderPreparedEvent> eventHandler;

    @RabbitListener(queues = "${rabbitmq.queues.inbound.orderPreparedQueue}")
    public void onMessage(OrderPreparedEvent orderPreparedEvent) {
        eventHandler.handleUpdate(orderPreparedEvent);
    }
}
