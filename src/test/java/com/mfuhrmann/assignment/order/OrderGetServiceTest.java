package com.mfuhrmann.assignment.order;

import com.mfuhrmann.assignment.order.view.GetOrdersResponse;
import com.mfuhrmann.assignment.product.ProductDocument;
import com.mfuhrmann.assignment.product.ProductDocumentRepository;
import com.mfuhrmann.assignment.user.Email;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderGetServiceTest {

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

    @ParameterizedTest
    @MethodSource("pricesStream")
    public void shouldCalculateTotalOrderAmount(Set<BigDecimal> prices, BigDecimal total) {
        //Given
        List<ProductDocument> products = createPersistedProductsWithPrices(prices);
        String email = "user@gmail.com";
        orderDocumentRepository.save(new OrderDocument(new Email(email), products));

        //When
        GetOrdersResponse ordersResponse = orderService.getOrders(
                Instant.now().minusSeconds(TimeUnit.HOURS.toSeconds(1)),
                Instant.now().plusSeconds(TimeUnit.HOURS.toSeconds(1)));

        //Then
        assertThat(ordersResponse.getTotal()).isEqualTo(total);
    }

    @ParameterizedTest
    @MethodSource("multipleOrderArguments")
    public void shouldCalculateTotalOrderAmountForMultipleOrders(Set<Set<BigDecimal>> priceGroups, BigDecimal total) {
        //Given
        String email = "user@gmail.com";
        List<List<ProductDocument>> productGroups = priceGroups.stream()
                .map(this::createPersistedProductsWithPrices)
                .collect(Collectors.toList());
        productGroups.forEach(productDocuments -> orderDocumentRepository.save(new OrderDocument(new Email(email), productDocuments)));

        //When
        GetOrdersResponse ordersResponse = orderService.getOrders(
                Instant.now().minusSeconds(TimeUnit.HOURS.toSeconds(1)),
                Instant.now().plusSeconds(TimeUnit.HOURS.toSeconds(1)));

        //Then
        assertThat(ordersResponse.getTotal()).isEqualTo(total);
    }

    @ParameterizedTest
    @MethodSource("searchDateArguments")
    public void shouldReturnData(Instant from, Instant to, int count) {

        //Given
        List<ProductDocument> products = createPersistedProductsWithPrices(Set.of(BigDecimal.ONE));
        String email = "user@gmail.com";
        orderDocumentRepository.save(new OrderDocument(new Email(email), products));

        //When
        GetOrdersResponse ordersResponse = orderService.getOrders(from, to);

        //Then
        assertThat(ordersResponse.getOrders()).hasSize(count);
    }


    private static Stream<Arguments> pricesStream() {
        return Stream.of(
                Arguments.of(Set.of(BigDecimal.valueOf(1)), BigDecimal.valueOf(1)),
                Arguments.of(Set.of(BigDecimal.valueOf(3.33), BigDecimal.valueOf(6.1000)), BigDecimal.valueOf(9.43)),
                Arguments.of(Collections.emptySet(), BigDecimal.ZERO)
        );
    }

    private static Stream<Arguments> multipleOrderArguments() {
        return Stream.of(
                Arguments.of(Set.of(Collections.emptySet()), BigDecimal.ZERO),
                Arguments.of(Set.of(Set.of(BigDecimal.valueOf(1), BigDecimal.valueOf(3)), Set.of(BigDecimal.valueOf(2))), BigDecimal.valueOf(6))
        );
    }

    private static Stream<Arguments> searchDateArguments() {
        return Stream.of(
                Arguments.of(Instant.now().minusSeconds(TimeUnit.HOURS.toSeconds(2)), Instant.now().minusSeconds(TimeUnit.MINUTES.toSeconds(1)), 0),
                Arguments.of(Instant.now().plusSeconds(TimeUnit.MINUTES.toSeconds(1)), Instant.now().plusSeconds(TimeUnit.HOURS.toSeconds(2)), 0),
                Arguments.of(Instant.now().minusSeconds(TimeUnit.MINUTES.toSeconds(1)), Instant.now().plusSeconds(TimeUnit.MINUTES.toSeconds(1)), 1)
        );
    }

    private List<ProductDocument> createPersistedProductsWithPrices(Set<BigDecimal> prices) {
        List<ProductDocument> products = prices.stream()
                .map(this::createRandomProductwithPrice)
                .collect(Collectors.toList());

        return productDocumentRepository.saveAll(products);
    }

    private ProductDocument createRandomProductwithPrice(BigDecimal price) {
        String sku = UUID.randomUUID().toString();
        String name = "name";
        return new ProductDocument(sku, name, price);
    }


}
