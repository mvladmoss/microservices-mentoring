package com.epam.shop.rabbitmq.service;

public interface MessageQueueProducer {

    <T> void sendMessage(String topic, T data);
}
