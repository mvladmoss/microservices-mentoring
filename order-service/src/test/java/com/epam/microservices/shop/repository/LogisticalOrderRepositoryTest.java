package com.epam.microservices.shop.repository;

import com.epam.microservices.shop.model.entity.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LogisticalOrderRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private LogisticalOrderRepository logisticalOrderRepository;

    @Test
    public void findLogisticalOrderByOrderIdentifier_succeed() {
        LogisticalOrderLine logisticalOrderLine = LogisticalOrderLine.builder()
                .price(BigDecimal.TEN)
                .productSku("dasd")
                .quantity(3)
                .build();

        Payment payment = Payment.builder()
                .status(PaymentStatus.AUTHORIZED)
                .transactionId("dasdasd")
                .externalPaymentId(321L)
                .created(Instant.now())
                .modified(Instant.now())
                .build();

        String orderIdentifier = UUID.randomUUID().toString();
        LogisticalOrder logisticalOrder = LogisticalOrder.builder()
                .totalAmount(BigDecimal.TEN)
                .logicStatus(OrderLogicStatus.WAITING_VERIFICATION)
                .orderLines(new HashSet<>(Collections.singletonList(logisticalOrderLine)))
                .orderIdentifier(orderIdentifier)
                .created(Instant.now())
                .modified(Instant.now())
                .payment(payment)
                .externalCustomerId(321L)
                .externalShopId(123L)
                .build();

        testEntityManager.persist(logisticalOrder);
        Optional<LogisticalOrder> maybeLogisticalOrder = logisticalOrderRepository.findByOrderIdentifier(orderIdentifier);
        Assert.assertTrue(maybeLogisticalOrder.isPresent());
        LogisticalOrder logOrder = maybeLogisticalOrder.get();
        Assert.assertEquals(logOrder, logisticalOrder);
    }
}
