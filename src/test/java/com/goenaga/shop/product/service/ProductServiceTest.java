package com.goenaga.shop.product.service;

import com.goenaga.shop.product.exception.DuplicatedProductException;
import com.goenaga.shop.product.mapper.ProductMapper;
import com.goenaga.shop.product.model.NewProductRequest;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.model.ProductDetails;
import com.goenaga.shop.product.repository.ProductRepository;
import com.goenaga.shop.product.service.impl.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    private final Product mockedProduct = Product.builder()
            .productId(123)
            .price(15)
            .currency(Currency.getInstance("USD"))
            .description("Mocked Desc")
            .name("Mocked product")
            .build();

    private final NewProductRequest mockedRequest = NewProductRequest.builder()
            .name("Mocked product")
            .build();

    private final ProductDetails mockedDetails = ProductDetails.builder()
            .price(15)
            .currency(Currency.getInstance("USD"))
            .description("Mocked Desc")
            .name("Mocked product")
            .build();

    @Test
    void getProducts_ReturnsProductDetailsList() {
        List<Product> productList = new ArrayList<>();
        productList.add(mockedProduct);

        Mockito.when(productRepository.findAll()).thenReturn(productList);
        Mockito.when(productMapper.productToProductDetails(any(Product.class))).thenReturn(mockedDetails);

        List<ProductDetails> expectedList = productService.getProducts();

        Assertions.assertThat(expectedList).isNotNull().isInstanceOf(ArrayList.class);
        Assertions.assertThat(expectedList.getFirst()).isInstanceOf(ProductDetails.class);
        Assertions.assertThat(expectedList.size()).isEqualTo(productList.size());
    }

    @Test
    void createNewProduct_ReturnsProductDetailsInstanceFromNewProductRequest() {
        when(productRepository.save(any(Product.class))).thenReturn(mockedProduct);
        when(productMapper.newProductRequestToProduct(any(NewProductRequest.class))).thenReturn(mockedProduct);
        when(productMapper.productToProductDetails(mockedProduct)).thenReturn(mockedDetails);

        ProductDetails actualProduct = productService.createNewProduct(mockedRequest);

        Assertions.assertThat(actualProduct).isNotNull().isInstanceOf(ProductDetails.class);
        Assertions.assertThat(actualProduct.getName()).isEqualTo(mockedProduct.getName());
    }

    @Test
    void createNewProduct_ThrowsExceptionIfProductAlreadyExists() {
        Product mockProduct = Product.builder().name("Mock request").build();

        when(productMapper.newProductRequestToProduct(any(NewProductRequest.class))).thenReturn(mockedProduct);
        when(productRepository.findByName(anyString())).thenReturn(Optional.of(mockedProduct));

        assertThrows(DuplicatedProductException.class, () -> productService.createNewProduct(mockedRequest) );
    }

    @Test
    void getProductById() {
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(mockedProduct));
        when(productMapper.productToProductDetails(any(Product.class))).thenReturn(mockedDetails);

        Assertions.assertThat(productService.getProductById(2)).isNotNull().isInstanceOf(ProductDetails.class);
    }

//    TODO
    @Test
    void updateProduct_ReturnsProductWithProvidedUpdatedDetails() {
        ProductDetails updateDetails = ProductDetails.builder()
                .name("Updated product")
                .description("Updated description")
                .price(23)
                .currency(Currency.getInstance("USD"))
                .build();
    }

//    TODO
    @Test
    void removeProduct() {
    }
}