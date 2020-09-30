package com.epam.microservices.shop.event.incoming;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerVerifiedEvent {

    private String orderIdentifier;
    private Boolean verified;
    private List<String> errors;
}
