package com.epam.microservices.shop.event.incoming;

import com.epam.microservices.shop.model.dto.ProductTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class OrderCreatedEvent {

    @NotNull
    private String orderIdentifier;
    @NotNull
    private Long externalShopId;
    @Valid
    @JsonProperty(value = "orderLines")
    private Set<ProductTO> products;
    private String address;
}
