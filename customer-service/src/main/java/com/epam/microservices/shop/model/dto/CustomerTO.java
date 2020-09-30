package com.epam.microservices.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerTO {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    private String login;
    private String password;
    @Valid
    private CreditCardTO creditCard;
    @Valid
    private AddressTO address;
}
