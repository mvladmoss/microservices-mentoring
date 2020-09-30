package com.epam.microservices.shop.service.orderupdate.handler;

public interface EventHandler<T> {

    void handleUpdate(T eventTO);

}
