package com.epam.microservices.shop.service.impl;

import com.epam.microservices.shop.model.dto.CustomerVerifiedEvent;
import com.epam.microservices.shop.model.entity.Customer;
import com.epam.microservices.shop.rabbitmq.model.OutboundQueues;
import com.epam.microservices.shop.repository.CustomerRepository;
import com.epam.microservices.shop.service.CustomerVerificationService;
import com.epam.microservices.shop.rabbitmq.service.MessageQueueProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.epam.microservices.shop.model.VerificationError.CUSTOMER_INACTIVE;

@Service
@RequiredArgsConstructor
public class CustomerVerificationServiceImpl implements CustomerVerificationService {

    private final CustomerRepository customerRepository;
    private final MessageQueueProducer messageQueueProducer;
    private final OutboundQueues outboundQueues;

    @Override
    @Transactional(readOnly = true)
    public void verify(Long customerId, String orderIdentifier) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Customer %s doesn't exist", customerId)));
        CustomerVerifiedEvent customerVerification;
        if (customer.getIsActive()) {
            customerVerification = CustomerVerifiedEvent.builder()
                    .orderIdentifier(orderIdentifier)
                    .verified(true)
                    .build();
        } else {
            customerVerification = CustomerVerifiedEvent.builder()
                    .orderIdentifier(orderIdentifier)
                    .verified(false)
                    .errors(Collections.singletonList(CUSTOMER_INACTIVE))
                    .build();
        }
        messageQueueProducer.sendMessage(outboundQueues.getCustomerVerifiedQueue().getRoutingKey(), customerVerification);
    }
}
