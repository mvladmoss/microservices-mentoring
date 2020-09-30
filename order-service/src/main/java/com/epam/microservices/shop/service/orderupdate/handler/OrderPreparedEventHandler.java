package com.epam.microservices.shop.service.orderupdate.handler;

import com.epam.microservices.shop.event.incoming.OrderPreparedEvent;
import com.epam.microservices.shop.model.entity.LogisticalOrder;
import com.epam.microservices.shop.model.entity.OrderLogicStatus;
import com.epam.microservices.shop.repository.LogisticalOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class OrderPreparedEventHandler implements EventHandler<OrderPreparedEvent> {

    private final LogisticalOrderRepository logisticalOrderRepository;

    @Override
    @Transactional
    public void handleUpdate(OrderPreparedEvent orderPreparedEvent) {
        LogisticalOrder logOrder = logisticalOrderRepository.findByOrderIdentifierAndLock(
                orderPreparedEvent.getOrderIdentifier());
        logOrder.setOrderDeliveryDate(orderPreparedEvent.getOrderCompletionDate());
        logOrder.setLogicStatus(OrderLogicStatus.SHIPPING);
        logOrder.setModified(Instant.now());
    }

}
