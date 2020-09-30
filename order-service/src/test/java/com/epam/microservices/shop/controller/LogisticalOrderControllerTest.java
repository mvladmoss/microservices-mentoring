package com.epam.microservices.shop.controller;

import com.epam.microservices.shop.model.dto.IncomingLogisticalOrderDto;
import com.epam.microservices.shop.model.dto.IncomingLogisticalOrderLineDto;
import com.epam.microservices.shop.model.dto.OrderDetailsTO;
import com.epam.microservices.shop.service.LogisticalOrderService;
import com.epam.microservices.shop.service.converter.IncomingEventConverter;
import com.epam.microservices.shop.service.converter.LogisticalOrderConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LogisticalOrderController.class)
public class LogisticalOrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LogisticalOrderService logisticalOrderService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final IncomingLogisticalOrderLineDto firstIncomingOrderLine = createIncomingOrderLine("Das1",
            BigDecimal.TEN, 2);
    private static final IncomingLogisticalOrderLineDto secondIncomingOrderLine = createIncomingOrderLine("Das2",
            BigDecimal.ONE, 3);
    private static final IncomingLogisticalOrderDto incomingOrder = createIncomingOrder(123L, 321L,
            "address", Arrays.asList(firstIncomingOrderLine, secondIncomingOrderLine));
    private static final String orderIdentifier = "orderIdentifier123";

    @Test
    @SneakyThrows
    public void createOrder_shouldReturnOrderIdentifier() throws Exception {
        when(logisticalOrderService.createOrder(incomingOrder)).thenReturn(orderIdentifier);

        mvc.perform(MockMvcRequestBuilders.post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(incomingOrder)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", Matchers.is(orderIdentifier)));

    }

    @Test
    @SneakyThrows
    public void getOrderDetails_shouldReturn200() throws Exception {
        OrderDetailsTO orderDetailsTO = createOrderDetailsTO();
        when(logisticalOrderService.getOrderDetails(orderDetailsTO.getOrderIdentifier())).thenReturn(orderDetailsTO);
        mvc.perform(MockMvcRequestBuilders.get("/orders/{orderIdentifier}", orderDetailsTO.getOrderIdentifier())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderIdentifier", Matchers.is(orderDetailsTO.getOrderIdentifier())))
                .andExpect(jsonPath("$.externalShopId", Matchers.is(orderDetailsTO.getExternalShopId()), Long.TYPE))
                .andExpect(jsonPath("$.orderStatus", Matchers.is(orderDetailsTO.getOrderStatus())))
                .andExpect(jsonPath("$.deliveryAddress", Matchers.is(orderDetailsTO.getDeliveryAddress())))
                .andExpect(jsonPath("$.orderCompletionDate", Matchers.is(orderDetailsTO.getOrderCompletionDate())))
                .andExpect(jsonPath("$.orderDeliveryDate", Matchers.is(orderDetailsTO.getOrderDeliveryDate())))
                .andExpect(jsonPath("$.orderLineDetails.size()", Matchers.is(orderDetailsTO
                        .getOrderLineDetails().size())));
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

    private static OrderDetailsTO createOrderDetailsTO() {
        return LogisticalOrderConverter.convert(IncomingEventConverter.convert(incomingOrder));
    }
}
