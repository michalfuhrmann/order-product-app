package com.mfuhrmann.assignment.product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @PersistenceConstructor)
@Getter
@Document(collection = "product")
public class ProductDocument {

    @Id
    private String id;
    @Indexed(unique = true)
    private final String sku;
    private final Instant creationDate;
    private boolean deleted;
    private String name;
    private BigDecimal price;

    public ProductDocument(String sku, String name, BigDecimal price) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.creationDate = Instant.now();
        this.deleted = false;
    }

    public void update(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public void delete() {
        this.deleted = true;
    }
}
