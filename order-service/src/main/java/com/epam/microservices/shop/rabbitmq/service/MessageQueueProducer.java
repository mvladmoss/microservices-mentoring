package com.epam.microservices.shop.rabbitmq.service;

public interface MessageQueueProducer {

    <T> void sendMessage(String routingKey, T data);

    <T> void sendMessage(String fanoutExchange, String routingKey, T data);
}
