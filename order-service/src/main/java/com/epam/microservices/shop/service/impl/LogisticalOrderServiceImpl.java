package com.epam.microservices.shop.service.impl;

import com.epam.microservices.shop.model.dto.IncomingLogisticalOrderDto;
import com.epam.microservices.shop.model.dto.OrderDetailsTO;
import com.epam.microservices.shop.model.entity.LogisticalOrder;
import com.epam.microservices.shop.model.entity.LogisticalOrderLine;
import com.epam.microservices.shop.event.outgoing.OrderCreatedEvent;
import com.epam.microservices.shop.repository.LogisticalOrderLineRepository;
import com.epam.microservices.shop.repository.LogisticalOrderRepository;
import com.epam.microservices.shop.service.LogisticalOrderService;
import com.epam.microservices.shop.service.converter.IncomingEventConverter;
import com.epam.microservices.shop.service.converter.LogisticalOrderConverter;
import com.epam.microservices.shop.service.converter.OutgoingEventBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LogisticalOrderServiceImpl implements LogisticalOrderService {

    private final LogisticalOrderRepository logisticalOrderRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public String createOrder(IncomingLogisticalOrderDto logisticalOrderDto) {
        Objects.requireNonNull(logisticalOrderDto, "Incoming logisticalOrderDto must not be null");
        LogisticalOrder logisticalOrder = IncomingEventConverter.convert(logisticalOrderDto);
        logisticalOrderRepository.save(logisticalOrder);

        Set<LogisticalOrderLine> logisticalOrderLines = logisticalOrderDto.getOrderLines().stream()
                .map(IncomingEventConverter::convert)
                .peek(line -> line.setLogisticalOrder(logisticalOrder))
                .collect(Collectors.toSet());
        logisticalOrder.setOrderLines(logisticalOrderLines);

        eventPublisher.publishEvent(OrderCreatedEvent.of(OutgoingEventBuilder.buildOrderCreatedEventTO(logisticalOrder)));
        return logisticalOrder.getOrderIdentifier();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailsTO getOrderDetails(String orderIdentifier) {
        LogisticalOrder logOrder = logisticalOrderRepository.findByOrderIdentifier(orderIdentifier)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Order with identifier %s doesn't exist",
                        orderIdentifier)));
        return LogisticalOrderConverter.convert(logOrder);
    }

    @Override
    public String getOrderStatus(String orderIdentifier) {
        LogisticalOrder logOrder = logisticalOrderRepository.findByOrderIdentifier(orderIdentifier)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Order with identifier %s doesn't exist",
                        orderIdentifier)));
        return logOrder.getLogicStatus().name();
    }


}
