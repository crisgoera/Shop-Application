package com.goenaga.shop.product.controller;

import com.goenaga.shop.error.ErrorResponse;
import com.goenaga.shop.product.model.NewProductDTO;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() { return ResponseEntity.ok(productService.getProducts()); }

    @PostMapping("/new")
    public ResponseEntity<Product> createProduct(@RequestBody NewProductDTO productRequest) throws IOException {
        return ResponseEntity.ok(productService.createNewProduct(productRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable String id) {
        if (productService.getProductById(id).isEmpty()) {
            ErrorResponse error = ErrorResponse.builder()
                    .message("Resource not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity editProductDetails(@PathVariable String id, @RequestBody Product updateDetails) {
        Optional<Product> foundProduct = productService.getProductById(id);
        if (foundProduct.isEmpty()) {
            ErrorResponse error = ErrorResponse.builder()
                    .message("Resource not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return ResponseEntity.ok(productService.updateProduct(foundProduct.get(), updateDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeProduct(@PathVariable String id) {
        Optional<Product> foundProduct = productService.getProductById(id);
        if (foundProduct.isEmpty()) {
            ErrorResponse error = ErrorResponse.builder()
                    .message("Resource not found")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
        return productService.removeProduct(foundProduct.get());
    }
}
