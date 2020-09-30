package com.epam.microservices.shop.rabbitmq.consumer;

import com.epam.microservices.shop.event.incoming.CustomerVerifiedEvent;
import com.epam.microservices.shop.service.orderupdate.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerVerifiedEventConsumer {

    private final EventHandler<CustomerVerifiedEvent> eventHandler;

    @RabbitListener(queues = "${rabbitmq.queues.inbound.customerVerifiedQueue}")
    public void onMessage(CustomerVerifiedEvent customerVerifiedEvent) {
        eventHandler.handleUpdate(customerVerifiedEvent);
    }
}
