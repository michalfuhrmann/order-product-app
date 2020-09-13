package com.mfuhrmann.assignment.product;

import com.mfuhrmann.assignment.product.create.CreateProductRequest;
import com.mfuhrmann.assignment.product.create.CreateProductResponse;
import com.mfuhrmann.assignment.product.update.UpdateProductRequest;
import com.mfuhrmann.assignment.product.view.GetAllProductsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiResponses({
            @ApiResponse(code = 201, message = "Product Created"),
            @ApiResponse(code = 409, message = "Product with sku exists")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CreateProductResponse createProduct(@RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Product updated"),
            @ApiResponse(code = 404, message = "Product not found")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}")
    void updateProduct(@PathVariable String id, @RequestBody UpdateProductRequest updateProductRequest) {
        productService.updateProduct(id, updateProductRequest);
    }

    @ApiResponses({
            @ApiResponse(code = 204, message = "Product updated"),
            @ApiResponse(code = 404, message = "Product not found")})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
    }

    @ApiResponses({@ApiResponse(code = 200, message = "Product retrieved")})
    @GetMapping
    GetAllProductsResponse getAllProducts() {
        return productService.getAllProducts();
    }

}
