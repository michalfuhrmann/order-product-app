package com.mfuhrmann.assignment.order;

import com.mfuhrmann.assignment.order.create.CreateOrderRequest;
import com.mfuhrmann.assignment.order.create.CreateOrderResponse;
import com.mfuhrmann.assignment.product.MultipleProductsNotFoundException;
import com.mfuhrmann.assignment.product.ProductDocument;
import com.mfuhrmann.assignment.product.ProductDocumentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class OrderCreateServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDocumentRepository orderDocumentRepository;
    @Autowired
    private ProductDocumentRepository productDocumentRepository;

    @BeforeEach
    public void before() {
        cleanDb();
    }

    @AfterEach
    public void after() {
        cleanDb();
    }

    private void cleanDb() {
        orderDocumentRepository.deleteAll();
        productDocumentRepository.deleteAll();
    }

    @Test
    public void shouldPersistNewOrder() {
        //Given
        Instant testBegin = Instant.now();
        List<String> productIds = getPersistedProducts();
        String email = "user@gmail.com";

        //When
        CreateOrderResponse order = orderService.createOrder(new CreateOrderRequest(email, productIds));

        //Then
        OrderDocument dbOrder = orderDocumentRepository.findById(order.getId()).orElseThrow();
        assertThat(order.getId()).isNotNull();
        assertThat(dbOrder.getEmail().getName()).isEqualTo(email);
        assertThat(dbOrder.getOrderTimeStamp()).isBetween(testBegin, Instant.now());
        assertThat(dbOrder.getProductDocuments().stream().map(ProductDocument::getId).collect(Collectors.toList()))
                .containsExactlyInAnyOrderElementsOf(productIds);
    }


    @Test
    public void shouldThrowExceptionIfProductDoesNotExist() {
        //Given
        getPersistedProducts();
        String email = "user@gmail.com";
        String productId = UUID.randomUUID().toString();

        //When
        CreateOrderRequest request = new CreateOrderRequest(email, List.of(productId));
        MultipleProductsNotFoundException exception = catchThrowableOfType(() -> orderService.createOrder(request), MultipleProductsNotFoundException.class);

        //Then
        assertThat(exception.getIds()).containsExactlyInAnyOrderElementsOf(Set.of(productId));
    }


    private List<String> getPersistedProducts() {
        ProductDocument randomProduct1 = createRandomProduct();
        ProductDocument randomProduct2 = createRandomProduct();
        List<ProductDocument> productDocuments = productDocumentRepository.saveAll(Set.of(randomProduct1, randomProduct2));
        return productDocuments.stream()
                .map(ProductDocument::getId)
                .collect(Collectors.toList());
    }


    private ProductDocument createRandomProduct() {
        String sku = UUID.randomUUID().toString();
        String name = "name";
        BigDecimal price = BigDecimal.valueOf(Math.random() * 100);
        return new ProductDocument(sku, name, price);
    }


}
