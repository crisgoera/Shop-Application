package com.goenaga.shop.product.controller;

import com.goenaga.shop.product.model.NewProductRequest;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.model.ProductDetails;
import com.goenaga.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDetails>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @PostMapping("/new")
    public ResponseEntity<Product> createProduct(@RequestBody NewProductRequest productRequest) {
        return ResponseEntity.ok(productService.createNewProduct(productRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PatchMapping("/{id}/edit")
    public ResponseEntity<?> editProductDetails(@PathVariable String id, @RequestBody ProductDetails updateDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, updateDetails));
    }

    @DeleteMapping("/{id}")
    public void removeProduct(@PathVariable String id) {
        productService.removeProduct(id);
    }

//    TODO: Fix POSTMAN image upload on requests to test photo implementation
//    @PatchMapping(path = "{id}/addphotos", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
//    public ResponseEntity addProductPhotos(@PathVariable String id, @RequestPart MultipartFile[] files,
//        @RequestPart String[] titles) throws IOException {
//        Optional<Product> foundProduct = productService.getProductById(id);
//        if (foundProduct.isEmpty()) {
//            ErrorResponse error = ErrorResponse.builder()
//                    .message("Resource not found")
//                    .build();
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//        }
//        Product updatedProduct = productService.addProductPhotos(foundProduct.get(), titles, files);
//
//        return ResponseEntity.ok(updatedProduct);
//    }

//    TODO: Remove photos from product functionality
}
