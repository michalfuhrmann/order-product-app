package com.mfuhrmann.assignment.product;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class GetAllProductsView {

    private final String id;
    private final String sku;
    private final Instant creationDate;
    private final String name;
    private final BigDecimal price;

    public GetAllProductsView(ProductDocument product) {
        this.id = product.getId();
        this.sku = product.getSku();
        this.creationDate = product.getCreationDate();
        this.name = product.getName();
        this.price = product.getPrice();
    }
}
