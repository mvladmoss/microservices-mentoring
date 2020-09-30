package com.epam.microservices.shop.service.impl;

import com.epam.microservices.shop.event.outgoing.ShopCatalogVerifiedEvent;
import com.epam.microservices.shop.event.incoming.OrderCreatedEvent;
import com.epam.microservices.shop.model.dto.OrderDetailsTO;
import com.epam.microservices.shop.model.entity.LogisticalOrder;
import com.epam.microservices.shop.model.entity.Product;
import com.epam.microservices.shop.model.entity.ProductOrder;
import com.epam.microservices.shop.model.entity.Shop;
import com.epam.microservices.shop.rabbitmq.model.OutboundQueues;
import com.epam.microservices.shop.repository.LogisticalOrderRepository;
import com.epam.microservices.shop.repository.ProductOrderRepository;
import com.epam.microservices.shop.repository.ProductRepository;
import com.epam.microservices.shop.repository.ShopRepository;
import com.epam.microservices.shop.service.MessageQueueProducer;
import com.epam.microservices.shop.service.LogisticalOrderService;
import com.epam.microservices.shop.service.converter.LogisticalOrderConverter;
import com.epam.microservices.shop.service.converter.OutgoingEventConverter;
import com.epam.microservices.shop.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.microservices.shop.model.entity.LogisticalOrderStatus.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogisticalOrderServiceImpl implements LogisticalOrderService {

    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;
    private final LogisticalOrderRepository logisticalOrderRepository;
    private final MessageQueueProducer messageQueueProducer;
    private final OutboundQueues outboundQueues;
    private final ShopRepository shopRepository;

    @Override
    @Transactional
    public void createOrder(OrderCreatedEvent orderCreatedEvent) {
        Map<Product, Integer> productToQuantity = orderCreatedEvent.getProducts().stream()
                .map(line -> {
                    Product product = productRepository.findByProductSkuAndShopId(line.getProductSku(),
                            orderCreatedEvent.getExternalShopId())
                            .orElseThrow(() -> new IllegalArgumentException(String.format("Product with sku %s for shop %s " +
                                    "doesn't exist", line.getProductSku(), orderCreatedEvent.getExternalShopId())));
                    return new Pair<>(product, line.getQuantity());
                })
                .collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
        LogisticalOrder logisticalOrder = LogisticalOrder.builder()
                .externalOrderIdentifier(orderCreatedEvent.getOrderIdentifier())
                .shopId(orderCreatedEvent.getExternalShopId())
                .status(WAITING_ACCEPTANCE)
                .created(Instant.now())
                .build();

        productToQuantity.forEach((key, value) -> {
            ProductOrder productOrder = ProductOrder.builder()
                    .product(key)
                    .logisticalOrder(logisticalOrder)
                    .quantity(value)
                    .build();
            productOrderRepository.save(productOrder);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetailsTO> findWaitingAcceptanceOrders(Long shopId) {
        return logisticalOrderRepository.findByExternalShopIdAndStatus(shopId, WAITING_ACCEPTANCE.name()).stream()
                .map(LogisticalOrderConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void acceptOrder(String orderIdentifier, Instant completionDate) {
        LogisticalOrder logisticalOrder = logisticalOrderRepository.findByExternalOrderIdentifier(orderIdentifier)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Order with identifier %s doesn't exist",
                        orderIdentifier)));
        if (logisticalOrder.getStatus().equals(WAITING_FOR_PREPARING)) {
            log.warn("Logistical order {} has already in status {}", orderIdentifier, WAITING_FOR_PREPARING);
            return;
        }
        logisticalOrder.setStatus(WAITING_FOR_PREPARING);
        logisticalOrder.setCompletionDate(completionDate);
        messageQueueProducer.sendMessage(outboundQueues.getShopCatalogVerifiedQueue().getRoutingKey(),
                ShopCatalogVerifiedEvent.builder()
                        .orderIdentifier(orderIdentifier)
                        .verified(true)
                        .build());
    }

    @Override
    @Transactional
    public void confirmOrder(String orderIdentifier) {
        LogisticalOrder logisticalOrder = logisticalOrderRepository.findByExternalOrderIdentifier(orderIdentifier)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Order with identifier %s doesn't exist",
                        orderIdentifier)));
        //send confirmation to external shop
        logisticalOrder.setStatus(PREPARING);
    }

    @Override
    @Transactional
    public void completeOrder(String orderIdentifier, Instant completionDate) {
        LogisticalOrder logisticalOrder = logisticalOrderRepository.findByExternalOrderIdentifier(orderIdentifier)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Order with identifier %s doesn't exist",
                        orderIdentifier)));
        logisticalOrder.setStatus(COMPLETED);
        logisticalOrder.setCompletionDate(completionDate);
        messageQueueProducer.sendMessage(outboundQueues.getOrderPreparedQueue().getRoutingKey(),
                OutgoingEventConverter.convert(logisticalOrder));
        Shop shop =  shopRepository.getOne(logisticalOrder.getShopId());
        messageQueueProducer.sendMessage(outboundQueues.getOrderReadyForDeliveryQueue().getRoutingKey(),
                OutgoingEventConverter.convert(logisticalOrder, shop));
    }
}
