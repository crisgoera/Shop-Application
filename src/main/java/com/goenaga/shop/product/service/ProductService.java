package com.goenaga.shop.product.service;

import com.goenaga.shop.product.mapper.ProductMapper;
import com.goenaga.shop.product.model.NewProductRequest;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.model.ProductDetails;
import com.goenaga.shop.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductDetails> getProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDetails> productDetailsList = new ArrayList<>();

        productList.forEach((product) -> {
            productDetailsList.add(productMapper.productToProductDetails(product));
        });

        return productDetailsList;
    }

    public Product createNewProduct(NewProductRequest productRequest) {
        return productRepository.save(productMapper.newProductRequestToProduct(productRequest));
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Product product, ProductDetails updateDetails) {
        return productRepository.save(productMapper.productDetailsToProduct(updateDetails));
    }

    public ResponseEntity removeProduct(Product product) {
        productRepository.delete(product);
        return new ResponseEntity(HttpStatus.OK);
    }

    private double roundPrice(double price) {
        return Math.round(price * 100.00)/100.00;
    }
}
