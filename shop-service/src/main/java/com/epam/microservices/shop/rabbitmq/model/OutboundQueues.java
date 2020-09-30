package com.epam.microservices.shop.rabbitmq.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@ConfigurationProperties(prefix = "rabbitmq.queues.outbound")
@Getter
@Setter
@Component
@Validated
public class OutboundQueues {

    @NotNull private QueueSettings shopCatalogVerifiedQueue;
    @NotNull private QueueSettings orderPreparedQueue;
    @NotNull private QueueSettings orderReadyForDeliveryQueue;
    ;
}
