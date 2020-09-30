package com.epam.microservices.shop.event.incoming;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderPreparedEvent {

    private String orderIdentifier;
    private Instant orderCompletionDate;
}
