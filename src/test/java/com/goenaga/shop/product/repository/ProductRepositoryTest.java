package com.goenaga.shop.product.repository;

import com.goenaga.shop.product.model.Product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    private void mockSetup() {
        Product mockedProduct1 = Product.builder()
                .name("Test 1")
                .price(25)
                .currency(Currency.getInstance("EUR"))
                .build();
        productRepository.save(mockedProduct1);

        Product mockedProduct2 = Product.builder()
                .name("Test 2")
                .price(39)
                .currency(Currency.getInstance("EUR"))
                .build();
        productRepository.save(mockedProduct2);
    }


    @Test
    void findAll() {
        var productList = productRepository.findAll();

        Assertions.assertThat(productList).isNotNull().isInstanceOf(List.class);
        assertEquals(productList.size(), 2);
        assertEquals(productList.get(0).getName(),  "Test 1");
        assertEquals(productList.get(1).getName(), "Test 2");
    }
}