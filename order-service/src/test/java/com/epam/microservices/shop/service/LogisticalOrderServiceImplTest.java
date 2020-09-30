package com.epam.microservices.shop.service;

import com.epam.microservices.shop.event.dto.OrderCreatedEventTO;
import com.epam.microservices.shop.event.outgoing.OrderCreatedEvent;
import com.epam.microservices.shop.model.dto.IncomingLogisticalOrderDto;
import com.epam.microservices.shop.model.dto.IncomingLogisticalOrderLineDto;
import com.epam.microservices.shop.model.dto.OrderDetailsTO;
import com.epam.microservices.shop.model.entity.LogisticalOrder;
import com.epam.microservices.shop.model.entity.LogisticalOrderLine;
import com.epam.microservices.shop.model.entity.OrderLogicStatus;
import com.epam.microservices.shop.repository.LogisticalOrderRepository;
import com.epam.microservices.shop.service.impl.LogisticalOrderServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LogisticalOrderServiceImplTest {

    @Mock
    private LogisticalOrderRepository logisticalOrderRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Captor
    private ArgumentCaptor<LogisticalOrder> logisticalOrderCaptor;
    @Captor
    private ArgumentCaptor<OrderCreatedEvent> orderCreatedEventCaptor;
    @InjectMocks
    private LogisticalOrderServiceImpl logisticalOrderService;

    private static final IncomingLogisticalOrderLineDto firstIncomingOrderLine = createIncomingOrderLine("Das1",
            BigDecimal.TEN, 2);
    private static final IncomingLogisticalOrderLineDto secondIncomingOrderLine = createIncomingOrderLine("Das2",
            BigDecimal.ONE, 3);
    private static final IncomingLogisticalOrderDto incomingOrder = createIncomingOrder(123L, 321L,
            "address", Arrays.asList(firstIncomingOrderLine, secondIncomingOrderLine));
    private static final String orderIdentifier = UUID.randomUUID().toString();

    @Test
    public void createOrder_shouldPassAndPublishOrderCreatedEvent() {
        logisticalOrderService.createOrder(incomingOrder);
        verify(logisticalOrderRepository, times(1)).save(logisticalOrderCaptor.capture());
        LogisticalOrder logisticalOrder = logisticalOrderCaptor.getValue();

        Assert.assertEquals(logisticalOrder.getLogicStatus(), OrderLogicStatus.WAITING_VERIFICATION);
        Assert.assertEquals(logisticalOrder.getDeliveryAddress(), incomingOrder.getDeliveryAddress());
        Assert.assertEquals(logisticalOrder.getExternalCustomerId(), incomingOrder.getExternalCustomerId());
        Assert.assertEquals(logisticalOrder.getExternalShopId(), incomingOrder.getExternalShopId());

        verify(eventPublisher, times(1)).publishEvent(orderCreatedEventCaptor.capture());
        OrderCreatedEventTO orderCreatedEventTO = orderCreatedEventCaptor.getValue().getOrderCreatedEventTO();

        Assert.assertEquals(orderCreatedEventTO.getAddress(), incomingOrder.getDeliveryAddress());
        Assert.assertEquals(orderCreatedEventTO.getCustomerId(), incomingOrder.getExternalCustomerId());
        Assert.assertEquals(orderCreatedEventTO.getExternalShopId(), incomingOrder.getExternalShopId());
        Assert.assertEquals(orderCreatedEventTO.getOrderIdentifier(), logisticalOrder.getOrderIdentifier());
    }

    @Test
    public void getOrderDetails_shouldReturnSucceed() {
        LogisticalOrder logisticalOrder = createLogisticalOrder(orderIdentifier);
        when(logisticalOrderRepository.findByOrderIdentifier(orderIdentifier)).thenReturn(Optional.ofNullable(logisticalOrder));
        OrderDetailsTO orderDetails = logisticalOrderService.getOrderDetails(orderIdentifier);

        Assert.assertEquals(orderDetails.getDeliveryAddress(), logisticalOrder.getDeliveryAddress());
        Assert.assertEquals(orderDetails.getExternalShopId(), logisticalOrder.getExternalShopId());
        Assert.assertEquals(orderDetails.getOrderIdentifier(), logisticalOrder.getOrderIdentifier());
        Assert.assertEquals(orderDetails.getOrderStatus(), logisticalOrder.getLogicStatus().name());
        Assert.assertEquals(orderDetails.getTotalAmount(), logisticalOrder.getTotalAmount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getOrderDetails_shouldFailed() {
        LogisticalOrder logisticalOrder = createLogisticalOrder(orderIdentifier);
        when(logisticalOrderRepository.findByOrderIdentifier(orderIdentifier)).thenReturn(Optional.ofNullable(logisticalOrder));
        logisticalOrderService.getOrderDetails("fakeOrderIdentifier");
    }

    private static IncomingLogisticalOrderDto createIncomingOrder(Long customerId, Long shopId, String deliveryAddress,
                                                                  List<IncomingLogisticalOrderLineDto> lines) {
        return IncomingLogisticalOrderDto.builder()
                .externalCustomerId(customerId)
                .externalShopId(shopId)
                .deliveryAddress(deliveryAddress)
                .orderLines(new HashSet<>(lines))
                .build();
    }

    private static IncomingLogisticalOrderLineDto createIncomingOrderLine(String sku, BigDecimal price, Integer quantity) {
        return IncomingLogisticalOrderLineDto.builder()
                .productSku(sku)
                .price(price)
                .quantity(quantity)
                .build();
    }

    private static LogisticalOrder createLogisticalOrder(String orderIdentifier) {
        LogisticalOrderLine logisticalOrderLine = LogisticalOrderLine.builder()
                .productSku("dsad")
                .price(BigDecimal.TEN)
                .quantity(2)
                .build();

        return LogisticalOrder.builder()
                .externalCustomerId(321L)
                .externalShopId(123L)
                .logicStatus(OrderLogicStatus.WAITING_VERIFICATION)
                .orderIdentifier(orderIdentifier)
                .totalAmount(BigDecimal.TEN)
                .deliveryAddress("address")
                .orderLines(new HashSet<>(Collections.singletonList(logisticalOrderLine)))
                .build();
    }

}
