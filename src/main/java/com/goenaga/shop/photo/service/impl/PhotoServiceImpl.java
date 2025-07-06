package com.goenaga.shop.photo.service.impl;

import com.goenaga.shop.photo.model.Photo;
import com.goenaga.shop.photo.service.PhotoService;
import com.goenaga.shop.photo.service.UploadService;
import com.goenaga.shop.product.mapper.ProductMapper;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.model.ProductDetails;
import com.goenaga.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    private final ProductService productService;
    private final UploadService uploadService;
    private final ProductMapper productMapper;

    @Override
    public Map<String, String> uploadFile(MultipartFile file) throws IOException {
        return uploadService.uploadFile(file);
    }

    @Override
    public Photo createPhotoEntity(Map<String, String> uploadResponse, Product product) {
        String photoName = uploadResponse.get("original_filename");
        String photoUrl = uploadResponse.get("url");
        String photoId = uploadResponse.get("asset_id");

        return Photo.builder()
                .photoId(photoId)
                .photoName(photoName)
                .photoUrl(photoUrl)
                .product(product)
                .build();
    }

    public ProductDetails addPhotoToProduct(int productId, MultipartFile file) throws IOException {
        Product foundProduct = productService.getProductEntityById(productId);
        List<Photo> productPhotoList = foundProduct.getPhotoList();

        Map<String, String> uploadResponse = this.uploadFile(file);

        Photo newPhoto = this.createPhotoEntity(uploadResponse, foundProduct);

        productPhotoList.add(newPhoto);
        foundProduct.setPhotoList(productPhotoList);

        return productMapper.productToProductDetails(productService.save(foundProduct));
    }
}
