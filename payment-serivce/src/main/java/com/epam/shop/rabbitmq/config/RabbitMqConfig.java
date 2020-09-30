package com.epam.shop.rabbitmq.config;

import com.epam.shop.rabbitmq.model.OutboundQueues;
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
    public Queue creditCardAuthorizationQueue() {
        return new Queue(outboundQueues.getCreditCardAuthorizedQueue().getName(), false);
    }

    @Bean
    public Binding creditCardAuthorizationQueueBinding(Queue creditCardAuthorizationQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(creditCardAuthorizationQueue).to(topicExchange)
                .with(outboundQueues.getCreditCardAuthorizedQueue().getRoutingKey());
    }

    @Bean
    public TopicExchange exchange(@Value("${rabbitmq.default.exchange}") String defaultExchange) {
        return new TopicExchange(defaultExchange);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
