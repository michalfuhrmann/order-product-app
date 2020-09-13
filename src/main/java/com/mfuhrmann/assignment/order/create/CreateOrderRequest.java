package com.mfuhrmann.assignment.order.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CreateOrderRequest {

    private final String email;
    private final List<String> productIds;

}
