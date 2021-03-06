package com.epam.microservices.shop.model.entity;

public enum OrderLogicStatus {

    WAITING_VERIFICATION,
    WAITING_CUSTOMER_VERIFICATION,
    WAITING_ORDER_VERIFICATION,
    WAITING_CREDIT_CARD_AUTHORIZATION,
    WAITING_ORDER_PREPARATION,
    SHIPPING,
    DELIVERED
}
