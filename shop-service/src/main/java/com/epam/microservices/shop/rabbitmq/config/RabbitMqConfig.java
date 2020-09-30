package com.epam.microservices.shop.rabbitmq.config;

import com.epam.microservices.shop.rabbitmq.model.OutboundQueues;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfig {

    private final OutboundQueues outboundQueues;

    @Bean
    public Queue shopCatalogVerifiedQueue() {
        return new Queue(outboundQueues.getShopCatalogVerifiedQueue().getName(), true);
    }

    @Bean
    public Queue orderPreparedQueue() {
        return new Queue(outboundQueues.getOrderPreparedQueue().getName(), true);
    }

    @Bean
    public Queue orderReadyForDeliveryQueue() {
        return new Queue(outboundQueues.getOrderReadyForDeliveryQueue().getName(), true);
    }

    @Bean
    public Binding shopCatalogVerifiedQueueBinding(Queue shopCatalogVerifiedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(shopCatalogVerifiedQueue).to(exchange).with(
                outboundQueues.getShopCatalogVerifiedQueue().getRoutingKey());
    }

    @Bean
    public Binding orderPreparedQueueBinding(Queue orderPreparedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderPreparedQueue).to(exchange).with(
                outboundQueues.getOrderPreparedQueue().getRoutingKey());
    }

    @Bean
    public Binding orderReadyForDeliveryQueueBinding(Queue orderReadyForDeliveryQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderReadyForDeliveryQueue).to(exchange).with(
                outboundQueues.getOrderReadyForDeliveryQueue().getRoutingKey());
    }

    @Bean
    public TopicExchange exchange(@Value("${rabbitmq.default.exchange}") String defaultExchange) {
        return new TopicExchange(defaultExchange);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
