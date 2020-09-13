package com.mfuhrmann.assignment.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OrderDocumentRepository extends MongoRepository<OrderDocument, String> {

    List<OrderDocument> findAllByOrderTimeStampBetween(Instant from, Instant to);
}
