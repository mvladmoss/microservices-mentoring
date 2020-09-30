package com.epam.shop.rabbitmq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMqProducer implements MessageQueueProducer {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public <T> void sendMessage(String routingKey, T data) {
        rabbitTemplate.convertAndSend(routingKey, data);
    }
}
