package com.mfuhrmann.assignment.order.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class GetOrdersRequest {

    private final Instant from;
    private final Instant to;

}
