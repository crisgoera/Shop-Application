package com.goenaga.shop.product.controller;

import com.goenaga.shop.auth.error.ErrorResponse;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() { return ResponseEntity.ok(productService.getProducts()); }

    @PostMapping("/new")
    public ResponseEntity<Product> createProduct(@RequestBody Product productRequest) {
        return ResponseEntity.ok(productService.createNewProduct(productRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable int id) {
        if (productService.getProductById(id).isEmpty()) {
            ErrorResponse error = ErrorResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("Resource not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return ResponseEntity.ok(productService.getProductById(id));
    }

}
