package com.mfuhrmann.assignment.product.update;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class UpdateProductRequest {

    private final String name;
    private final BigDecimal price;
}
