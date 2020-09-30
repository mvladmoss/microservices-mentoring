package com.epam.microservice.shop.model.dto;

import com.epam.microservice.shop.model.entity.Courier;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LogisticalOrderTO {

    @NotNull
    private String orderIdentifier;
    @NotNull
    private Long externalShopId;
    @NotNull
    private Instant completionDate;
    @NotNull
    private String address;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "courier_id")
    private Courier courier;
}
