package com.goenaga.shop.product.service;

import com.goenaga.shop.product.mapper.ProductMapper;
import com.goenaga.shop.product.model.NewProductRequest;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.model.ProductDetails;
import com.goenaga.shop.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Spy
    private ProductMapper productMapper;

    @Test
    void getProducts_ReturnsProductDetailsList() {
        final Product mockedProduct1 = Product.builder()
                .productId(1L)
                .name("Test product").price(15)
                .currency(Currency.getInstance("EUR"))
                .build();

        List<Product> productList = new ArrayList<>();
        productList.add(mockedProduct1);

        Mockito.when(productRepository.findAll()).thenReturn(productList);
        Mockito.when(productMapper.productToProductDetails(any(Product.class))).thenReturn(ProductDetails.builder().name("Test product").build());

        List<ProductDetails> expectedList = productService.getProducts();

        Assertions.assertThat(expectedList).isNotNull().isInstanceOf(ArrayList.class);
        Assertions.assertThat(expectedList.get(0)).isInstanceOf(ProductDetails.class);
        Assertions.assertThat(expectedList.size()).isEqualTo(productList.size());
    }

    @Test
    void createNewProduct_ReturnsProductDetailsInstanceFromNewProductRequest() {

    }

    @Test
    void getProductById() {

    }

    @Test
    void updateProduct_ReturnsProductWithProvidedUpdatedDetails() {
        ProductDetails updateDetails = ProductDetails.builder()
                .name("Updated product")
                .description("Updated description")
                .price(23)
                .currency(Currency.getInstance("USD"))
                .build();


    }

    @Test
    void removeProduct() {
    }
}