package com.mfuhrmann.assignment.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProductCreateServiceTest {

    @Autowired
    private ProductDocumentRepository productDocumentRepository;

    @Autowired
    private ProductService productService;

    @BeforeEach
    public void before() {
        cleanDb();
    }

    @AfterEach
    public void after() {
        cleanDb();
    }

    private void cleanDb() {
        productDocumentRepository.deleteAll();
    }

    @Test
    public void shouldPersistNewProductInDbAndSetTheFields() {
        //Given
        Instant testBegin = Instant.now();
        String sku = "sku";
        String name = "name";
        BigDecimal price = BigDecimal.valueOf(100.1);
        CreateProductRequest createProductRequest = new CreateProductRequest(sku, name, price);

        //When
        CreateProductResponse product = productService.createProduct(createProductRequest);

        //Then
        assertThat(product.getSku()).isEqualTo(sku);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getCreationDate()).isBetween(testBegin, Instant.now());
        assertThat(productDocumentRepository.findByIdAndDeletedIsFalse(product.getId())).isPresent();
    }

    @Test
    public void shouldThrowExceptionIfSkuExists() {
        //Given
        CreateProductRequest productRequest = createSampleRequest();
        productDocumentRepository.save(new ProductDocument(productRequest.getSku(), "name", BigDecimal.valueOf(1)));

        //When
        DuplicateSKUException skuException = catchThrowableOfType(
                () -> productService.createProduct(productRequest), DuplicateSKUException.class);

        //Then
        assertThat(skuException.getSku()).isEqualTo(productRequest.getSku());
    }

    private CreateProductRequest createSampleRequest() {
        String sku = "sku";
        String name = "name";
        BigDecimal price = BigDecimal.valueOf(100.1);
        return new CreateProductRequest(sku, name, price);
    }


}
