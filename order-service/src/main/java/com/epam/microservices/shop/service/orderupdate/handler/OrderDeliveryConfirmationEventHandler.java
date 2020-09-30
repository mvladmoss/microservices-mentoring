package com.epam.microservices.shop.service.orderupdate.handler;

import com.epam.microservices.shop.event.incoming.OrderDeliveryConfirmationEvent;
import com.epam.microservices.shop.model.entity.LogisticalOrder;
import com.epam.microservices.shop.repository.LogisticalOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static com.epam.microservices.shop.model.entity.OrderLogicStatus.DELIVERED;

@Service
@RequiredArgsConstructor
public class OrderDeliveryConfirmationEventHandler implements EventHandler<OrderDeliveryConfirmationEvent> {

    private final LogisticalOrderRepository logisticalOrderRepository;

    @Override
    @Transactional
    public void handleUpdate(OrderDeliveryConfirmationEvent orderDeliveryConfirmationEvent) {
        LogisticalOrder logOrder = logisticalOrderRepository.findByOrderIdentifierAndLock(
                orderDeliveryConfirmationEvent.getOrderIdentifier());
        logOrder.setLogicStatus(DELIVERED);
        logOrder.setModified(Instant.now());
    }
}
