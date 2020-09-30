package com.epam.microservices.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreditCardTO {

    @NotNull
    private String bankProvider;
    @NotNull
    private String cardNumber;
    @NotNull
    private String expirationDate;
    @NotNull
    private String cvsCode;
}
