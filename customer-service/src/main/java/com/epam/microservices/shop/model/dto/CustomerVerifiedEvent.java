package com.epam.microservices.shop.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerVerifiedEvent {

    private Boolean verified;
    private List<String> errors;
    private String orderIdentifier;
}
