package org.example.productservice.controller;

import org.example.common.ApiResponse;
import org.example.productservice.model.Product;
import org.example.productservice.model.ProductRequest;
import org.example.productservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> saveProduct(@RequestBody ProductRequest productRequest) {
        Product product = productService.createProduct(productRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("A product has been created")
                .status(HttpStatus.CREATED)
                .code(201)
                .payload(product)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAllProducts() {
        List<Product> products = productService.getAllProducts();
        ApiResponse apiResponse = ApiResponse.builder()
                .message("All products has been received")
                .status(HttpStatus.OK)
                .code(200)
                .payload(products)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("A product has been received")
                .status(HttpStatus.OK)
                .code(200)
                .payload(product)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCustomer(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        Product product = productService.updateProduct(id, productRequest);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("A product with id " + id + " has been updated")
                .status(HttpStatus.OK)
                .code(200)
                .payload(product)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("A product with id " + id + " has been deleted")
                .status(HttpStatus.OK)
                .code(200)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
