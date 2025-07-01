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

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetails> getProductById(@PathVariable int id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<ProductDetails> editProductDetails(@PathVariable int id, @RequestBody ProductDetails updateDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, updateDetails));
    }

    @DeleteMapping("/{id}")
    public void removeProduct(@PathVariable int id) {
        productService.removeProduct(id);
    }

    @PostMapping(value = "/{id}/photos/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDetails> addPhotosToProduct(@PathVariable int productId,
                                                  @RequestPart PhotoFile photoFile) throws IOException {
        return ResponseEntity.ok(productService.addPhotoToProduct(productId, photoFile));
    }
}
