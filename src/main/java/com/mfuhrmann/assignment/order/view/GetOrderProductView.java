package com.mfuhrmann.assignment.order.view;

import com.mfuhrmann.assignment.product.ProductDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@Getter
public class GetOrderProductView {

    private final String id;
    private final String sku;
    private final Instant creationDate;
    private final String name;
    private final BigDecimal price;

    public GetOrderProductView(ProductDocument productDocument) {
        this.id = productDocument.getId();
        this.sku = productDocument.getSku();
        this.creationDate = productDocument.getCreationDate();
        this.name = productDocument.getName();
        this.price = productDocument.getPrice();
    }
}
