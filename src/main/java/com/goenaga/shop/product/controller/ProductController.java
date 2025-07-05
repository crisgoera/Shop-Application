package com.goenaga.shop.product.controller;

import com.goenaga.shop.photo.model.PhotoFile;
import com.goenaga.shop.photo.service.UploadService;
import com.goenaga.shop.product.model.NewProductRequest;
import com.goenaga.shop.product.model.ProductDetails;
import com.goenaga.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    @GetMapping
    public ResponseEntity<List<ProductDetails>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @PostMapping("/new")
    public ResponseEntity<ProductDetails> createProduct(@RequestBody NewProductRequest productRequest) {
        return ResponseEntity.ok(productService.createNewProduct(productRequest));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetails> getProductById(@PathVariable int productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PatchMapping("/{productId}/edit")
    public ResponseEntity<ProductDetails> editProductDetails(@PathVariable int productId, @RequestBody ProductDetails updateDetails) {
        return ResponseEntity.ok(productService.updateProduct(productId, updateDetails));
    }

    @DeleteMapping("/{productId}")
    public void removeProduct(@PathVariable int productId) {
        productService.removeProduct(productId);
    }

    @PostMapping(value = "/{productId}/photos/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDetails> addPhotosToProduct(@PathVariable int productId,
                                                  @RequestPart(value = "file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(productService.addPhotoToProduct(productId, file));
    }
}
