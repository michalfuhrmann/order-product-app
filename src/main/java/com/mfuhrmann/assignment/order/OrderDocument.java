package com.mfuhrmann.assignment.order;

import com.mfuhrmann.assignment.product.ProductDocument;
import com.mfuhrmann.assignment.user.Email;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE, onConstructor_ = @PersistenceConstructor)
@Getter
@Document(collection = "order")
public class OrderDocument {

    @Id
    private String id;
    private final Instant orderTimeStamp;
    private final Email email;
    private final List<ProductDocument> productDocuments;

    public OrderDocument(Email email, List<ProductDocument> productDocuments) {
        this.email = email;
        this.productDocuments = productDocuments;
        this.orderTimeStamp = Instant.now();
    }
}

