package com.mfuhrmann.assignment.product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDocumentRepository extends MongoRepository<ProductDocument, String> {

    List<ProductDocument> findAllByDeletedIsFalse();
    List<ProductDocument> findAllByIdInAndDeletedIsFalse(Collection<String> ids) ;

    Optional<ProductDocument> findByIdAndDeletedIsFalse(String id);

    boolean existsBySku(String sku);

    default List<ProductDocument> findAllActive() {
        return findAllByDeletedIsFalse();
    }
}
