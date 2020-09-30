package com.epam.microservices.shop.rabbitmq.config;

import com.epam.microservices.shop.rabbitmq.model.OutboundQueues;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
    public Queue customerVerifiedQueue() {
        return new Queue(outboundQueues.getCustomerVerifiedQueue().getName(), false);
    }

    @Bean
    public Binding customerVerifiedQueueBinding(Queue customerVerifiedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(customerVerifiedQueue).to(exchange).with(
                outboundQueues.getCustomerVerifiedQueue().getRoutingKey());
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
