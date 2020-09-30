package com.epam.microservices.shop.rabbitmq.consumer;

import com.epam.microservices.shop.event.incoming.CreditCardAuthorizedEvent;
import com.epam.microservices.shop.service.orderupdate.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditCardAuthorizedEventConsumer {

    private final EventHandler<CreditCardAuthorizedEvent> eventHandler;

    @RabbitListener(queues = "${rabbitmq.queues.inbound.creditCardAuthorizedQueue}")
    public void onMessage(CreditCardAuthorizedEvent creditCardAuthorizedEvent) {
        eventHandler.handleUpdate(creditCardAuthorizedEvent);
    }
}
