package com.epam.shop.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class QueueSettings {

    private String name;
    private String routingKey;
}
