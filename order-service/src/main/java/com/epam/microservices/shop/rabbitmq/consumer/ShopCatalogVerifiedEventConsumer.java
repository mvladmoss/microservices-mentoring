package com.epam.microservices.shop.rabbitmq.consumer;

import com.epam.microservices.shop.event.incoming.ShopCatalogVerifiedEvent;
import com.epam.microservices.shop.service.orderupdate.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShopCatalogVerifiedEventConsumer {

    private final EventHandler<ShopCatalogVerifiedEvent> eventHandler;

    @RabbitListener(queues = "${rabbitmq.queues.inbound.shopCatalogVerifiedQueue}")
    public void onMessage(ShopCatalogVerifiedEvent catalogVerifiedEvent) {
        eventHandler.handleUpdate(catalogVerifiedEvent);
    }
}
