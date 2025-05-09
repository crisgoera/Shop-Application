package com.goenaga.shop.product.service.impl;

import com.goenaga.shop.product.exception.DuplicatedProductException;
import com.goenaga.shop.product.mapper.ProductMapper;
import com.goenaga.shop.product.model.NewProductRequest;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.model.ProductDetails;
import com.goenaga.shop.product.repository.ProductRepository;
import com.goenaga.shop.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
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

    public ProductDetails createNewProduct(NewProductRequest productRequest) {
        Product newProduct = productMapper.newProductRequestToProduct(productRequest);
        boolean exists = productRepository.findByName(newProduct.getName()).isPresent();

        if (exists) { throw new DuplicatedProductException(); }
        Product saved = productRepository.save(newProduct);

//        Return ProductDetails DTO
        return productMapper.productToProductDetails(newProduct);
    }

    public ProductDetails getProductById(int id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return productMapper.productToProductDetails(foundProduct.get());
    }

    public ProductDetails updateProduct(int id, ProductDetails updateDetails) {
        Optional<Product> foundProduct = productRepository.findById(id);
        Product updatedProduct = productMapper.updateDetailsToProduct(updateDetails, foundProduct.get());
        productRepository.save(updatedProduct);

        return productMapper.productToProductDetails(updatedProduct);
    }

    public void removeProduct(int id) {

    }

    private double roundPrice(double price) {
        return Math.round(price * 100.00)/100.00;
    }
}
