package com.epam.shop.rabbitmq.consumer;

import com.epam.shop.event.incoming.CreditCardAuthorizationEvent;
import com.epam.shop.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreditCardAuthorizationEventConsumer {

    private final PaymentService paymentService;

    @RabbitListener(queues = "${rabbitmq.queues.inbound.creditCardAuthorizationQueue}")
    public void onMessage(CreditCardAuthorizationEvent creditCardAuthorizationEvent) {
        paymentService.authorize(creditCardAuthorizationEvent);
    }
}
