//package com.epam.microservices.shop.rabbitmq;
//
//import com.epam.microservices.shop.ApplicationInitializer;
//import lombok.RequiredArgsConstructor;
//import org.junit.Assert;
//import org.junit.jupiter.api.Test;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.test.context.ContextConfiguration;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import static com.epam.microservices.shop.rabbitmq.RabbitMqTestConfiguration.*;
//
//@SpringBootTest(classes = ApplicationInitializer.class)
//@ContextConfiguration(classes = RabbitMqTestConfiguration.class)
//@Testcontainers
//@RequiredArgsConstructor
//class RabbitMqIntegrationTest {
//
//    @Container
//    static GenericContainer<?> rabbitMQContainer = new GenericContainer<>("rabbitmq:management")
//            .withExposedPorts(5672);
//
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    @DynamicPropertySource
//    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.rabbitmq.port", () -> rabbitMQContainer.getMappedPort(5672));
//    }
//
//    @Test
//    void shouldSendMessageToQueueAndReceiveMessageThen() throws Exception {
//        String testMessage = "message";
//        rabbitTemplate.convertAndSend(TEST_EXCHANGE, ROUTING_KEY, testMessage);
//        String message = rabbitTemplate.receiveAndConvert(TEST_QUEUE, ParameterizedTypeReference.forType(String.class));
//        Assert.assertEquals(testMessage, message);
//    }
//}
