package com.epam.microservices.shop.feature;

import com.epam.microservices.shop.controller.LogisticalOrderController;
import com.epam.microservices.shop.event.dto.OrderCreatedEventTO;
import com.epam.microservices.shop.event.outgoing.OrderCreatedEvent;
import com.epam.microservices.shop.model.dto.IncomingLogisticalOrderDto;
import com.epam.microservices.shop.model.dto.IncomingLogisticalOrderLineDto;
import com.epam.microservices.shop.model.entity.LogisticalOrder;
import com.epam.microservices.shop.rabbitmq.model.OutboundQueues;
import com.epam.microservices.shop.repository.LogisticalOrderRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

public class CucumberOrderFlowDefs {

    private final CucumberOrderContextHolder cucumberOrderContextHolder = new CucumberOrderContextHolder();
    @Autowired
    private LogisticalOrderController logisticalOrderController;
    @Autowired
    private LogisticalOrderRepository logisticalOrderRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private OutboundQueues outboundQueues;

    @When("^incoming logistical order is generated$")
    public void generateIncomingLogisticalOrder() {
        IncomingLogisticalOrderLineDto incomingLogisticalOrderLineDto = IncomingLogisticalOrderLineDto.builder()
                .productSku("dasdas")
                .price(BigDecimal.TEN)
                .quantity(5)
                .build();

        IncomingLogisticalOrderDto incomingLogisticalOrderDto = IncomingLogisticalOrderDto.builder()
                .externalShopId(3L)
                .externalCustomerId(4L)
                .deliveryAddress("address")
                .orderLines(new HashSet<>(Collections.singletonList(incomingLogisticalOrderLineDto)))
                .build();

        cucumberOrderContextHolder.setIncomingLogisticalOrderDto(incomingLogisticalOrderDto);
    }

    @When("^service receives request for order creation with generated incoming logistical order$")
    public void receiveRequestForOrderCreation() {
        String orderIdentifier = logisticalOrderController.createOrder(cucumberOrderContextHolder.getIncomingLogisticalOrderDto());
        cucumberOrderContextHolder.setGeneratedOrderIdentifier(orderIdentifier);
        Optional<LogisticalOrder> maybeLogisticalOrder = logisticalOrderRepository.
                findByOrderIdentifier(cucumberOrderContextHolder.getGeneratedOrderIdentifier());
        Assert.assertTrue(maybeLogisticalOrder.isPresent());
        cucumberOrderContextHolder.setLogisticalOrder(maybeLogisticalOrder.get());

    }

    @Then("^check that created order has the same property values as incoming logistical order$")
    public void checkCreatedAndIncomingLogisticalOrders() {
        LogisticalOrder logisticalOrder = cucumberOrderContextHolder.getLogisticalOrder();
        IncomingLogisticalOrderDto incomingLogisticalOrder = cucumberOrderContextHolder.getIncomingLogisticalOrderDto();
        Assert.assertEquals(logisticalOrder.getExternalShopId(), incomingLogisticalOrder.getExternalShopId());
        Assert.assertEquals(logisticalOrder.getExternalCustomerId(), incomingLogisticalOrder.getExternalCustomerId());
        Assert.assertEquals(logisticalOrder.getDeliveryAddress(), incomingLogisticalOrder.getDeliveryAddress());
        Assert.assertEquals(logisticalOrder.getOrderIdentifier(), cucumberOrderContextHolder.getGeneratedOrderIdentifier());
        Assert.assertEquals(logisticalOrder.getTotalAmount(), incomingLogisticalOrder.getOrderLines().stream()
                .map(IncomingLogisticalOrderLineDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP));
    }

    @Then("^check order status is (\\w+)")
    public void checkOrderStatus(String status) {
        LogisticalOrder logisticalOrder = cucumberOrderContextHolder.getLogisticalOrder();
        Assert.assertEquals(logisticalOrder.getLogicStatus().name(), status);
    }

    @Then("^check that ORDER_CREATED_EVENT was generated and send$")
    public void checkOrderCreatedEventWasGenerated() {
        OrderCreatedEventTO orderCreatedEventFirst = rabbitTemplate.receiveAndConvert(outboundQueues.getOrderCreatedQueue().getName(),
                ParameterizedTypeReference.forType(OrderCreatedEventTO.class));
        Assert.assertEquals(orderCreatedEventFirst.getOrderIdentifier(), cucumberOrderContextHolder.getGeneratedOrderIdentifier());
        OrderCreatedEventTO orderCreatedEvenSecond = rabbitTemplate.receiveAndConvert(outboundQueues.getCustomerVerificationQueue().getName(),
                ParameterizedTypeReference.forType(OrderCreatedEventTO.class));
        Assert.assertEquals(orderCreatedEvenSecond.getOrderIdentifier(), cucumberOrderContextHolder.getGeneratedOrderIdentifier());
        Assert.assertEquals(orderCreatedEventFirst, orderCreatedEvenSecond);
    }
}
