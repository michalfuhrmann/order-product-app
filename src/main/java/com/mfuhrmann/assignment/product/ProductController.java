package com.mfuhrmann.assignment.product;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Api(tags = "products")
@RestController
@RequestMapping("/products")
class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CreateProductResponse createProduct(@RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}")
    void updateProduct(@PathVariable String id, @RequestBody UpdateProductRequest updateProductRequest) {
        productService.updateProduct(id, updateProductRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

    @GetMapping
    GetAllProductsResponse getAllProducts() {
        return productService.getAllProducts();
    }

}
