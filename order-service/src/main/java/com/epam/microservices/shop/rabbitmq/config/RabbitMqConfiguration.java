package com.epam.microservices.shop.rabbitmq.config;

import com.epam.microservices.shop.rabbitmq.model.OutboundQueues;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMqConfiguration {

    private final OutboundQueues outboundQueues;

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(outboundQueues.getOrderCreatedQueue().getName(), false);
    }

    @Bean
    public Queue customerVerificationQueue() {
        return new Queue(outboundQueues.getCustomerVerificationQueue().getName(), true);
    }

    @Bean
    public Queue creditCardAuthorizationQueue() {
        return new Queue(outboundQueues.getCreditCardAuthorizationQueue().getName(), true);
    }

    @Bean
    public Queue orderVerifiedQueue() {

        return new Queue(outboundQueues.getOrderVerifiedQueue().getName(), true);
    }

    @Bean
    public Binding customerVerificationQueueBinding(Queue customerVerificationQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(customerVerificationQueue).to(fanoutExchange);
    }

    @Bean
    public Binding orderCreatedQueueBinding(Queue orderCreatedQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(orderCreatedQueue).to(fanoutExchange);
    }

    @Bean
    public Binding creditCardAuthorizationBinding(Queue creditCardAuthorizationQueue, TopicExchange exchange) {
        return BindingBuilder.bind(creditCardAuthorizationQueue).to(exchange).with(
                outboundQueues.getCreditCardAuthorizationQueue().getRoutingKey());
    }

    @Bean
    public Binding orderVerifiedBinding(Queue orderVerifiedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderVerifiedQueue).to(exchange).with(
                outboundQueues.getOrderVerifiedQueue().getRoutingKey());
    }

    @Bean
    public TopicExchange exchange(@Value("${rabbitmq.default.exchange}") String defaultExchange) {
        return new TopicExchange(defaultExchange);
    }

    @Bean
    public FanoutExchange fanoutExchange(@Value("${rabbitmq.default.fanout-exchange}") String fanoutExchange) {
        return new FanoutExchange(fanoutExchange);
    }

    @Bean
    public RabbitTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
