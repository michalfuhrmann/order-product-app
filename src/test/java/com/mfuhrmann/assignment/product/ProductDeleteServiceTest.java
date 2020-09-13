package com.mfuhrmann.assignment.product;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductDeleteServiceTest {

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
    public void shouldDeleteProductById() {
        //Given
        ProductDocument existingDocument = productDocumentRepository.save(createSampleProduct());

        //When
        productService.deleteProduct(existingDocument.getId());

        //Then
        assertThat(productDocumentRepository.count()).isEqualTo(1);
        List<ProductDocument> allActive = productDocumentRepository.findAllActive();
        assertThat(allActive).hasSize(0);
    }

    @Test
    public void shouldThrowExceptionIfProductDoesNotExists() {
        //Given
        String nonExistingId = "non-existing-id";

        //When
        ProductNotFoundException productNotFoundException = catchThrowableOfType(
                () -> productService.deleteProduct(nonExistingId), ProductNotFoundException.class);

        //Then
        assertThat(productNotFoundException.getId()).isEqualTo(nonExistingId);
    }

    private ProductDocument createSampleProduct() {
        String sku = "sku";
        String name = "name";
        BigDecimal price = BigDecimal.valueOf(100.1);
        return new ProductDocument(sku, name, price);
    }


}
