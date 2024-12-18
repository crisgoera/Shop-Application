package com.goenaga.shop.product.mapper;

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
                .price(15)
                .currency(Currency.getInstance("EUR"))
                .build();

        Product expectedProduct = Product.builder()
                .productId(1L)
                .name("Test product")
                .price(15)
                .currency(Currency.getInstance("EUR"))
                .build();

        Product actualProduct = productMapper.productDetailsToProduct(productDetails);

        assertEquals(actualProduct.getName(), expectedProduct.getName());
        assertEquals(actualProduct.getProductId(), expectedProduct.getProductId());
        assertEquals(actualProduct.getPrice(), expectedProduct.getPrice());
    }

    @Test
    void productToProductDetails_ReturnsMappedProductDetailsObject() {
        Product product = Product.builder()
                .productId(1L)
                .name("Test product")
                .price(15)
                .currency(Currency.getInstance("EUR"))
                .build();

        ProductDetails expectedDetails = ProductDetails.builder()
                .productId(1L)
                .name("Test product")
                .price(15)
                .currency(Currency.getInstance("EUR"))
                .build();

        ProductDetails actualDetails = productMapper.productToProductDetails(product);

        assertEquals(actualDetails.getName(), expectedDetails.getName());
        assertEquals(actualDetails.getProductId(), expectedDetails.getProductId());
        assertEquals(actualDetails.getPrice(), expectedDetails.getPrice());
    }
}
