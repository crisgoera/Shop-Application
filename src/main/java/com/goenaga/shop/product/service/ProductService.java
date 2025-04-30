package com.goenaga.shop.product.service;

import com.goenaga.shop.product.mapper.ProductMapper;
import com.goenaga.shop.product.model.NewProductRequest;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.model.ProductDetails;
import com.goenaga.shop.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

    public Product getProductById(String id) throws EntityNotFoundException{
        Optional<Product> foundProduct = productRepository.findById(id);
        if (foundProduct.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return foundProduct.get();
    }

    public Product updateProduct(String id, ProductDetails updateDetails) {
        Product product = getProductById(id);
        return productRepository.save(productMapper.updateDetailsToProduct(updateDetails, product));
    }

    public void removeProduct(String id) {
        Product product = getProductById(id);
        productRepository.delete(product);
    }

    private double roundPrice(double price) {
        return Math.round(price * 100.00)/100.00;
    }
}
