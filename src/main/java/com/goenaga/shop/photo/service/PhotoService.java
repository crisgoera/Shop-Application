package com.goenaga.shop.photo.service;

import com.goenaga.shop.photo.model.Photo;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.model.ProductDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface PhotoService {
    Map<String, String> uploadFile(MultipartFile file) throws IOException;
    Photo createPhotoEntity(Map<String, String> uploadResponse, Product product);
    ProductDetails addPhotoToProduct(int productId, MultipartFile multipartFile) throws IOException;
}
