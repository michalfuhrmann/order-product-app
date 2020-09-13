package com.mfuhrmann.assignment.order;

import com.mfuhrmann.assignment.order.create.CreateOrderRequest;
import com.mfuhrmann.assignment.order.create.CreateOrderResponse;
import com.mfuhrmann.assignment.order.view.GetOrdersResponse;
import com.mfuhrmann.assignment.product.MultipleProductsNotFoundException;
import com.mfuhrmann.assignment.product.ProductDocument;
import com.mfuhrmann.assignment.product.ProductDocumentRepository;
import com.mfuhrmann.assignment.user.Email;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderDocumentRepository orderDocumentRepository;
    private final ProductDocumentRepository productDocumentRepository;

    public OrderService(OrderDocumentRepository orderDocumentRepository,
                        ProductDocumentRepository productDocumentRepository) {
        this.orderDocumentRepository = orderDocumentRepository;
        this.productDocumentRepository = productDocumentRepository;
    }

    public CreateOrderResponse createOrder(CreateOrderRequest request) {

        List<ProductDocument> dbProducts = productDocumentRepository.findAllByIdInAndDeletedIsFalse(request.getProductIds());
        validateProducts(dbProducts, new HashSet<>(request.getProductIds()));

        OrderDocument persistedOrder = orderDocumentRepository.save(new OrderDocument(new Email(request.getEmail()), dbProducts));

        return new CreateOrderResponse(persistedOrder.getId());
    }

    public GetOrdersResponse getOrders(Instant from, Instant to) {
        List<OrderDocument> orders = orderDocumentRepository.findAllByOrderTimeStampBetween(from, to);

        return new GetOrdersResponse(orders);
    }

    private void validateProducts(List<ProductDocument> dbProducts, Set<String> productIds) {

        Set<String> dbProductIds = dbProducts.stream()
                .map(ProductDocument::getId)
                .collect(Collectors.toSet());

        boolean allProductsFound = dbProductIds.containsAll(productIds);

        if (!allProductsFound) {
            productIds.removeIf(dbProductIds::contains);
            throw new MultipleProductsNotFoundException(productIds);
        }
    }
}
