package org.example.orderservice.feignClient;

import org.example.common.ApiResponse;
import org.example.productservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service",
        url = "http://localhost:8082/api/v1/product")
public interface ProductClient {
    @GetMapping("/{id}")
    ApiResponse<Product> findProductById(@PathVariable("id") Long id);
}
