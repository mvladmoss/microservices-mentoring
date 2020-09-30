package com.epam.microservices.shop.rabbitmq;

import com.epam.microservices.shop.rabbitmq.config.RabbitMqConfiguration;
import com.epam.microservices.shop.rabbitmq.model.OutboundQueues;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
public class RabbitMqTestConfiguration {

    public static final String ROUTING_KEY = "notifications";
    public static final String TEST_EXCHANGE = "testcontainers.test.exchange";
    public static final String TEST_QUEUE = "testcontainers.test.queue";
    @Autowired
    private OutboundQueues outboundQueues;

    @Bean
    Queue queue() {
        return new Queue(TEST_QUEUE, false);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(TEST_EXCHANGE);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(outboundQueues.getOrderCreatedQueue().getName(), false);
    }

    @Bean
    public Queue customerVerificationQueue() {
        return new Queue(outboundQueues.getCustomerVerificationQueue().getName(), false);
    }

    @Bean
    public FanoutExchange fanoutExchange(@Value("${rabbitmq.default.fanout-exchange}") String fanoutExchange) {
        return new FanoutExchange(fanoutExchange);
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
