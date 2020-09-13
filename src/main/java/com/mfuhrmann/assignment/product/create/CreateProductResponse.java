package com.mfuhrmann.assignment.product.create;

import com.mfuhrmann.assignment.product.ProductDocument;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Getter
@AllArgsConstructor
public class CreateProductResponse {

    private final String id;
    private final String sku;
    private final Instant creationDate;
    private final String name;
    private final BigDecimal price;

    public CreateProductResponse(ProductDocument product) {
        this.id = product.getId();
        this.sku = product.getSku();
        this.creationDate = product.getCreationDate();
        this.name = product.getName();
        this.price = product.getPrice();
    }
}
