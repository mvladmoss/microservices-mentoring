package com.epam.microservices.shop.service;

public interface MessageQueueProducer {

    <T> void sendMessage(String topic, T data);
}
