package com.goenaga.shop.product.mapper;

import com.goenaga.shop.product.model.NewProductRequest;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.model.ProductDetails;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Currency;

import static junit.framework.TestCase.assertEquals;

public class ProductMapperTest {
    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Test
    void productDetailsToProduct_ReturnsMappedProductObject() {
        ProductDetails productDetails = ProductDetails.builder()
                .productId(1L)
                .name("Test product")
                .description("Random desc")
                .price(15)
                .currency(Currency.getInstance("EUR"))
                .build();

        Product expectedProduct = Product.builder()
                .productId(1L)
                .name("Test product")
                .description("Random desc")
                .price(15)
                .currency(Currency.getInstance("EUR"))
                .build();

        Product actualProduct = productMapper.productDetailsToProduct(productDetails);

        assertEquals(actualProduct.getProductId(), expectedProduct.getProductId());
        assertEquals(actualProduct.getName(), expectedProduct.getName());
        assertEquals(actualProduct.getDescription(), expectedProduct.getDescription());
        assertEquals(actualProduct.getPrice(), expectedProduct.getPrice());
        assertEquals(actualProduct.getCurrency(), expectedProduct.getCurrency());
    }

    @Test
    void productToProductDetails_ReturnsMappedProductDetailsObject() {
        Product product = Product.builder()
                .productId(1L)
                .name("Test product")
                .description("Random desc")
                .price(15)
                .currency(Currency.getInstance("EUR"))
                .build();

        ProductDetails expectedProductDetails = ProductDetails.builder()
                .productId(1L)
                .name("Test product")
                .description("Random desc")
                .price(15)
                .currency(Currency.getInstance("EUR"))
                .build();

        ProductDetails actualProductDetails = productMapper.productToProductDetails(product);

        assertEquals(actualProductDetails.getProductId(), expectedProductDetails.getProductId());
        assertEquals(actualProductDetails.getName(), expectedProductDetails.getName());
        assertEquals(actualProductDetails.getDescription(), expectedProductDetails.getDescription());
        assertEquals(actualProductDetails.getPrice(), expectedProductDetails.getPrice());
        assertEquals(actualProductDetails.getCurrency(), expectedProductDetails.getCurrency());
    }

    @Test
    void newProductRequestToProduct_ReturnsMappedProductObject() {
        NewProductRequest productRequest = NewProductRequest.builder()
                .name("Product request")
                .description("New product desc")
                .price(25)
                .currency(Currency.getInstance("EUR"))
                .build();

        Product expectedProduct = Product.builder()
                .name("Product request")
                .description("New product desc")
                .price(25)
                .currency(Currency.getInstance("EUR"))
                .build();

        Product actualProduct = productMapper.newProductRequestToProduct(productRequest);

        assertEquals(actualProduct.getName(), expectedProduct.getName());
        assertEquals(actualProduct.getDescription(), expectedProduct.getDescription());
        assertEquals(actualProduct.getPrice(), expectedProduct.getPrice());
        assertEquals(actualProduct.getCurrency(), expectedProduct.getCurrency());
    }
}
