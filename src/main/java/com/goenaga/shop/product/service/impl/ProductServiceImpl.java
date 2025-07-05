package com.goenaga.shop.product.service.impl;

import com.goenaga.shop.photo.model.Photo;
import com.goenaga.shop.photo.service.PhotoService;
import com.goenaga.shop.product.exception.DuplicatedProductException;
import com.goenaga.shop.product.exception.ProductNotFoundException;
import com.goenaga.shop.product.mapper.ProductMapper;
import com.goenaga.shop.product.model.NewProductRequest;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.model.ProductDetails;
import com.goenaga.shop.product.repository.ProductRepository;
import com.goenaga.shop.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final PhotoService photoService;

    public List<ProductDetails> getProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDetails> productDetailsList = new ArrayList<>();

        productList.forEach((product) -> {
            productDetailsList.add(productMapper.productToProductDetails(product));
        });

        return productDetailsList;
    }

    public ProductDetails createNewProduct(NewProductRequest productRequest) {
        Product newProduct = productMapper.newProductRequestToProduct(productRequest);
        boolean exists = productRepository.findByName(newProduct.getName()).isPresent();

        if (exists) {
            throw new DuplicatedProductException();
        }

        Product saved = productRepository.save(newProduct);

        return productMapper.productToProductDetails(saved);
    }

    public ProductDetails getProductById(int id) {
        Optional<Product> foundProduct = productRepository.findById(id);

        boolean exists = foundProduct.isPresent();
        if (!exists) {
            throw new ProductNotFoundException();
        }

        return productMapper.productToProductDetails(foundProduct.get());
    }

    public ProductDetails updateProduct(int id, ProductDetails updateDetails) {
        Optional<Product> foundProduct = productRepository.findById(id);

        boolean exists = foundProduct.isPresent();
        if (!exists) {
            throw new ProductNotFoundException();
        }

        Product updatedProduct = productMapper.updateDetailsToProduct(updateDetails, foundProduct.get());
        Product saved = productRepository.save(updatedProduct);

        return productMapper.productToProductDetails(saved);
    }

    public void removeProduct(int id) {
        Optional<Product> foundProduct = productRepository.findById(id);

        boolean exists = foundProduct.isPresent();
        if (!exists) {
            throw new ProductNotFoundException();
        }

        productRepository.delete(foundProduct.get());
    }

    public ProductDetails addPhotoToProduct(int productId, MultipartFile file) throws IOException {
        Optional<Product> foundProduct = productRepository.findById(productId);
        boolean exists = foundProduct.isPresent();
        if (!exists) {
            throw new ProductNotFoundException();
        }

        ProductDetails updateDetails = getProductById(productId);
        Map<String, String> uploadResponse = photoService.uploadFile(file);

        Photo newPhoto = photoService.createPhotoEntity(uploadResponse, productId);
        List<Photo> photoList = updateDetails.getPhotoList();
        photoList.add(newPhoto);
        updateDetails.setPhotoList(photoList);

        productRepository.save(productMapper.updateDetailsToProduct(updateDetails, foundProduct.get()));
        photoService.savePhoto(newPhoto);

        return updateDetails;
    }
}
