package org.example.productservice.service;

import org.example.productservice.model.Product;
import org.example.productservice.model.ProductRequest;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductRequest productRequest);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product updateProduct(Long id, ProductRequest productRequest);
    void deleteProduct(Long id);

}
