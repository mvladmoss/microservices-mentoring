package com.epam.microservices.shop.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class IncomingLogisticalOrderDto {

    @NotNull
    private Long externalCustomerId;
    @NotNull
    private Long externalShopId;
    @NotNull
    private String deliveryAddress;
    @Valid
    private Set<IncomingLogisticalOrderLineDto> orderLines;
}
