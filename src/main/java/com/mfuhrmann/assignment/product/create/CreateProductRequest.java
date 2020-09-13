package com.mfuhrmann.assignment.product.create;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;


@Getter
@AllArgsConstructor
public class CreateProductRequest {

    private final String sku;
    private final String name;
    private final BigDecimal price;
}
