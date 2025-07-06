package com.goenaga.shop.photo.controller;

import com.goenaga.shop.photo.service.PhotoService;
import com.goenaga.shop.product.model.ProductDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;

    @PostMapping(value = "/{productId}/photos/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDetails> addPhotosToProduct(@PathVariable int productId,
                                                             @RequestPart(value = "file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(photoService.addPhotoToProduct(productId, file));
    }
}
