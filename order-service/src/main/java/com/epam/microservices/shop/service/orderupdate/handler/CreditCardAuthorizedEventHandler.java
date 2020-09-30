package com.epam.microservices.shop.service.orderupdate.handler;

import com.epam.microservices.shop.event.dto.OrderVerifiedEventTO;
import com.epam.microservices.shop.event.outgoing.OrderVerifiedEvent;
import com.epam.microservices.shop.event.incoming.CreditCardAuthorizedEvent;
import com.epam.microservices.shop.model.entity.LogisticalOrder;
import com.epam.microservices.shop.model.entity.OrderLogicStatus;
import com.epam.microservices.shop.model.entity.Payment;
import com.epam.microservices.shop.model.entity.PaymentStatus;
import com.epam.microservices.shop.repository.LogisticalOrderRepository;
import com.epam.microservices.shop.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class CreditCardAuthorizedEventHandler implements EventHandler<CreditCardAuthorizedEvent> {

    private final LogisticalOrderRepository logisticalOrderRepository;
    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void handleUpdate(CreditCardAuthorizedEvent creditCardAuthorizedEvent) {
        LogisticalOrder logOrder = logisticalOrderRepository.findByOrderIdentifierAndLock(
                creditCardAuthorizedEvent.getOrderIdentifier());

        Instant now = Instant.now();
        Payment payment = Payment.builder()
                .externalPaymentId(creditCardAuthorizedEvent.getExternalPaymentId())
                .transactionId(creditCardAuthorizedEvent.getTransactionId())
                .status(PaymentStatus.AUTHORIZED)
                .logisticalOrder(logOrder)
                .created(now)
                .modified(now)
                .build();
        paymentRepository.save(payment);

        logOrder.setLogicStatus(OrderLogicStatus.WAITING_ORDER_PREPARATION);
        logOrder.setModified(Instant.now());
        eventPublisher.publishEvent(OrderVerifiedEvent.of(OrderVerifiedEventTO.builder()
                .orderIdentifier(creditCardAuthorizedEvent.getOrderIdentifier())
                .build()));
    }

}
