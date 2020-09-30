package com.epam.microservices.shop;

import com.epam.microservices.shop.controller.LogisticalOrderController;
import com.epam.microservices.shop.model.dto.OrderDetailsTO;
import com.epam.microservices.shop.service.LogisticalOrderService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.math.BigDecimal;

import static com.epam.microservices.shop.model.entity.OrderLogicStatus.WAITING_VERIFICATION;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationInitializer.class)
public abstract class ContractVerifierBase {
 
    @Autowired
    private LogisticalOrderController logisticalOrderController;
    @MockBean
    private LogisticalOrderService logisticalOrderService;

    @Before
    public void setup() {
        when(logisticalOrderService.getOrderDetails("1"))
                .thenReturn(OrderDetailsTO.builder()
                        .orderIdentifier("1")
                        .externalShopId(1L)
                        .deliveryAddress("address")
                        .orderStatus(WAITING_VERIFICATION.name())
                        .totalAmount(BigDecimal.TEN)
                        .build());

        StandaloneMockMvcBuilder standaloneMockMvcBuilder
          = MockMvcBuilders.standaloneSetup(logisticalOrderController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }
}