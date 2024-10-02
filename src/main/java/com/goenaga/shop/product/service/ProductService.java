package com.goenaga.shop.product.service;

import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getProducts() { return productRepository.findAll(); }

    public Product createNewProduct(Product productRequest) {
        return productRepository.save(productRequest);
    }
}
