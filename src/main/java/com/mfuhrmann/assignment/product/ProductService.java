package com.mfuhrmann.assignment.product;

import com.mfuhrmann.assignment.product.create.CreateProductRequest;
import com.mfuhrmann.assignment.product.create.CreateProductResponse;
import com.mfuhrmann.assignment.product.update.UpdateProductRequest;
import com.mfuhrmann.assignment.product.view.GetAllProductsResponse;
import com.mfuhrmann.assignment.product.view.GetAllProductsView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    private final ProductDocumentRepository productDocumentRepository;

    public ProductService(ProductDocumentRepository productDocumentRepository) {
        this.productDocumentRepository = productDocumentRepository;
    }

    public CreateProductResponse createProduct(CreateProductRequest request) {
        log.trace("Persisting new product: {}", request);
        checkSkuUniqueness(request);

        ProductDocument persistedDoc = productDocumentRepository.save(
                new ProductDocument(request.getSku(), request.getName(), request.getPrice()));

        return new CreateProductResponse(persistedDoc);
    }

    public void updateProduct(String id, UpdateProductRequest request) {
        log.trace("Updating product with id and body : {} , {}", id, request);
        ProductDocument productDocument = productDocumentRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productDocument.update(request.getName(), request.getPrice());

        productDocumentRepository.save(productDocument);
    }

    public void deleteProduct(String id) {
        log.trace("Deleting product with id : {}", id);
        ProductDocument productDocument = productDocumentRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productDocument.delete();

        productDocumentRepository.save(productDocument);
    }

    public GetAllProductsResponse getAllProducts() {
        List<GetAllProductsView> activeProducts = productDocumentRepository.findAllActive().stream()
                .map(GetAllProductsView::new)
                .collect(Collectors.toList());

        return new GetAllProductsResponse(activeProducts);
    }

    private void checkSkuUniqueness(CreateProductRequest request) {
        boolean skuExists = productDocumentRepository.existsBySku(request.getSku());
        if (skuExists) {
            throw new DuplicateSKUException(request.getSku());
        }
    }

}
