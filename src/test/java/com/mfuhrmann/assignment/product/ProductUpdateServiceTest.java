package com.mfuhrmann.assignment.product;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductUpdateServiceTest {

    @Autowired
    private ProductDocumentRepository productDocumentRepository;

    @Autowired
    private ProductService productService;

    @Test
    public void shouldUpdateTheFieldsForExistingProduct() {
        //Given
        ProductDocument existingDoc = productDocumentRepository.save(createSampleProduct());
        String id = existingDoc.getId();
        UpdateProductRequest updateRequest = createSampleRequest();

        //When
        productService.updateProduct(id, updateRequest);

        //Then
        ProductDocument product = productDocumentRepository.findById(existingDoc.getId()).orElseThrow();
        assertThat(product.getName()).isEqualTo(updateRequest.getName());
        assertThat(product.getPrice()).isEqualTo(updateRequest.getPrice());
        assertThat(product.getSku()).isEqualTo(existingDoc.getSku());
        assertThat(product.getCreationDate()).isEqualTo(existingDoc.getCreationDate().truncatedTo(ChronoUnit.MILLIS));// mongo truncates nanos, TODO investigate
        assertThat(product.isDeleted()).isEqualTo(false);
    }

    @Test
    public void shouldThrowExceptionIfProductDoesNotExists() {
        //Given
        UpdateProductRequest productRequest = createSampleRequest();
        String id = "non-existing-id";

        //When
        ProductNotFoundException exception = catchThrowableOfType(
                () -> productService.updateProduct(id, productRequest), ProductNotFoundException.class);

        //Then
        assertThat(exception.getId()).isEqualTo(id);
    }

    private UpdateProductRequest createSampleRequest() {
        String name = "name";
        BigDecimal price = BigDecimal.valueOf(100.1);
        return new UpdateProductRequest(name, price);
    }

    private ProductDocument createSampleProduct() {
        String sku = "sku";
        String name = "name";
        BigDecimal price = BigDecimal.valueOf(100.1);
        return new ProductDocument(sku, name, price);
    }

}
