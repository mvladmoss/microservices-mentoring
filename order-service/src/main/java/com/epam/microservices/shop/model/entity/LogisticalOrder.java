package com.epam.microservices.shop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orderLines", "payment"})
@ToString(exclude = {"orderLines", "payment"})
public class LogisticalOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long externalCustomerId;
    private Long externalShopId;
    private String orderIdentifier;
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    private OrderLogicStatus logicStatus;
    private String deliveryAddress;
    private Instant orderCompletionDate;
    private Instant orderDeliveryDate;
    private Instant created;
    private Instant modified;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.REMOVE}, mappedBy = "logisticalOrder")
    private Set<LogisticalOrderLine> orderLines;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "logisticalOrder")
    private Payment payment;
}
