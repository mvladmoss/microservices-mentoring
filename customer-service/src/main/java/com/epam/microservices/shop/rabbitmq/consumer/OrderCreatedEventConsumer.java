package com.epam.microservices.shop.rabbitmq.consumer;

import com.epam.microservices.shop.event.incoming.CustomerVerificationEvent;
import com.epam.microservices.shop.service.CustomerVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreatedEventConsumer {

    private final CustomerVerificationService customerVerificationService;

    @RabbitListener(queues = "${rabbitmq.queues.inbound.customerVerificationQueue}")
    public void OnMessage(CustomerVerificationEvent customerVerificationEvent) {
        customerVerificationService.verify(customerVerificationEvent.getCustomerId(),
                customerVerificationEvent.getOrderIdentifier());
    }
}
