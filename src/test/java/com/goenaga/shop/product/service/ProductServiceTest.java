package com.goenaga.shop.product.service;

import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Product product;

    @Test
    void getProducts_Returns_ProductList() {
        Product mockedProduct1 = mock(Product.class);
        productRepository.save(mockedProduct1);
        Product mockedProduct2 = mock(Product.class);
        productRepository.save(mockedProduct2);

        var productList = productService.getProducts();

        Assertions.assertThat(productList).isNotNull().isInstanceOf(List.class);
    }

    @Test
    void createNewProduct() {
    }

    @Test
    void getProductById() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void removeProduct() {
    }
}