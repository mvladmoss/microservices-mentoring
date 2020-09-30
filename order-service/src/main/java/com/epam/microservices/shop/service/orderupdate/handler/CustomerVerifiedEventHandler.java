package com.epam.microservices.shop.service.orderupdate.handler;

import com.epam.microservices.shop.event.outgoing.CreditCardAuthorizationEvent;
import com.epam.microservices.shop.event.incoming.CustomerVerifiedEvent;
import com.epam.microservices.shop.model.entity.LogisticalOrder;
import com.epam.microservices.shop.model.entity.OrderLogicStatus;
import com.epam.microservices.shop.repository.LogisticalOrderRepository;
import com.epam.microservices.shop.service.converter.OutgoingEventBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import static com.epam.microservices.shop.model.entity.OrderLogicStatus.WAITING_CREDIT_CARD_AUTHORIZATION;
import static com.epam.microservices.shop.model.entity.OrderLogicStatus.WAITING_CUSTOMER_VERIFICATION;
import static com.epam.microservices.shop.model.entity.OrderLogicStatus.WAITING_ORDER_VERIFICATION;

@Service
@RequiredArgsConstructor
public class CustomerVerifiedEventHandler implements EventHandler<CustomerVerifiedEvent> {

    private final LogisticalOrderRepository logisticalOrderRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void handleUpdate(CustomerVerifiedEvent customerVerifiedEvent) {
        LogisticalOrder logOrder = logisticalOrderRepository.findByOrderIdentifierAndLock(
                customerVerifiedEvent.getOrderIdentifier());
        OrderLogicStatus logicStatus = logOrder.getLogicStatus();
        logOrder.setModified(Instant.now());
        if (logicStatus != null && logicStatus.equals(WAITING_ORDER_VERIFICATION)) {
            logOrder.setLogicStatus(WAITING_CREDIT_CARD_AUTHORIZATION);
            eventPublisher.publishEvent(CreditCardAuthorizationEvent.of(OutgoingEventBuilder.
                    buildCreditCardAuthorizationEventTO(logOrder)));
        } else {
            logOrder.setLogicStatus(WAITING_CUSTOMER_VERIFICATION);
        }
    }

}
