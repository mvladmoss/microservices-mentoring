package com.epam.microservices.shop.service.impl;

import com.epam.microservices.shop.rabbitmq.model.OutboundQueues;
import com.epam.microservices.shop.event.outgoing.CreditCardAuthorizationEvent;
import com.epam.microservices.shop.event.outgoing.CreditCardCaptureEvent;
import com.epam.microservices.shop.event.outgoing.OrderVerifiedEvent;
import com.epam.microservices.shop.event.outgoing.OrderCreatedEvent;
import com.epam.microservices.shop.rabbitmq.service.MessageQueueProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class LogisticalOrderEventHandlerService {

    private final MessageQueueProducer messageQueueProducer;
    private final OutboundQueues outboundQueues;
    @Value("${rabbitmq.default.fanout-exchange}")
    private String fanountExchange;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        messageQueueProducer.sendMessage(fanountExchange, "", event.getOrderCreatedEventTO());
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleOrderCreatedEvent(CreditCardAuthorizationEvent event) {
        messageQueueProducer.sendMessage(outboundQueues.getCreditCardAuthorizationQueue().getRoutingKey(),
                event.getCreditCardAuthorizationEventTO());
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleOrderConfirmationEvent(OrderVerifiedEvent event) {
        messageQueueProducer.sendMessage(outboundQueues.getOrderVerifiedQueue().getRoutingKey(),
                event.getOrderVerifiedEventTO());
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleCreditCardCaptureEvent(CreditCardCaptureEvent event) {
        messageQueueProducer.sendMessage("", event.getLogOrder());
    }

}
