package com.epam.microservices.shop.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Pair<F, S> {

    private final F left;
    private final S right;
}
