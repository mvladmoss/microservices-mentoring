package com.epam.shop.service.impl;

import com.epam.shop.event.incoming.CreditCardAuthorizationEvent;
import com.epam.shop.model.dto.CreditCardTO;
import com.epam.shop.rabbitmq.model.OutboundQueues;
import com.epam.shop.web.configuration.ServicesConfiguration;
import lombok.RequiredArgsConstructor;
import com.epam.shop.event.outcoming.CreditCardAuthorizedEvent;
import com.epam.shop.model.entity.Payment;
import com.epam.shop.model.entity.PaymentStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.epam.shop.repository.PaymentRepository;
import com.epam.shop.rabbitmq.service.MessageQueueProducer;
import com.epam.shop.service.PaymentRemoteIntegrationService;
import com.epam.shop.service.PaymentService;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentRemoteIntegrationService remoteIntegrationService;
    private final MessageQueueProducer messageQueueProducer;
    private final WebClient customerIntegrationWebClient;
    private final ServicesConfiguration servicesConfiguration;
    private final OutboundQueues outboundQueues;

    @Override
    @Transactional
    public void authorize(CreditCardAuthorizationEvent creditCardAuthorizationEvent) {
        Retry retry = Retry.backoff(servicesConfiguration.getRetrySetting().getMaxAttempts(),
                Duration.ofSeconds(servicesConfiguration.getRetrySetting().getBackoffInSeconds()));
        Mono<CreditCardTO> creditCardTOMono = customerIntegrationWebClient.get()
                .uri(servicesConfiguration.getUrl().getCustomerCreditCard(), new Object[]{creditCardAuthorizationEvent.
                        getExternalCustomerId()})
                .retrieve()
                .bodyToMono(CreditCardTO.class)
                .retryWhen(retry);

        creditCardTOMono.subscribe((creditCard) -> {
            String transactionNumber = remoteIntegrationService.authorize(creditCardAuthorizationEvent.getTotalAmount(),
                    creditCard.getCardNumber(),
                    creditCard.getExpirationDate(), creditCard.getCvsCode());
            Instant now = Instant.now();
            Payment payment = Payment.builder()
                    .totalAmount(creditCardAuthorizationEvent.getTotalAmount())
                    .transactionNumber(transactionNumber)
                    .externalShopId(creditCardAuthorizationEvent.getExternalShopId())
                    .orderIdentifier(creditCardAuthorizationEvent.getOrderIdentifier())
                    .status(PaymentStatus.AUTHORIZED)
                    .created(now)
                    .modified(now)
                    .build();
            Long generatedId = paymentRepository.save(payment).getId();
            CreditCardAuthorizedEvent creditCardAuthorizedEvent = CreditCardAuthorizedEvent.builder()
                    .orderIdentifier(creditCardAuthorizationEvent.getOrderIdentifier())
                    .transactionId(transactionNumber)
                    .externalPaymentId(generatedId)
                    .build();
            messageQueueProducer.sendMessage(outboundQueues.getCreditCardAuthorizedQueue().getRoutingKey(),
                    creditCardAuthorizedEvent);
        });
    }

    @Override
    @Transactional
    public void capture(String transactionNumber) {
        remoteIntegrationService.capture(transactionNumber);
        Payment payment = paymentRepository.findByTransactionNumber(transactionNumber)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Payment with transaction number %s " +
                        "doesn't exist", transactionNumber)));
        payment.setStatus(PaymentStatus.CAPTURED);
        paymentRepository.save(payment);
    }

    @Override
    public String findPaymentStatus(String orderIdentifier) {
        Payment payment = paymentRepository.findByOrderIdentifier(orderIdentifier)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Payment with order identifier %s " +
                        "doesn't exist", orderIdentifier)));
        return payment.getStatus().name();
    }
}
