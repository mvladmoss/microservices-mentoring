package com.epam.microservices.shop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"products"})
@ToString(exclude = {"products"})
public class LogisticalOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String externalOrderIdentifier;
    @Enumerated(EnumType.STRING)
    private LogisticalOrderStatus status;
    private Instant created;
    private Instant completionDate;
    private Long shopId;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.REMOVE}, mappedBy = "logisticalOrder")
    private Set<ProductOrder> products = new HashSet<>();
}
