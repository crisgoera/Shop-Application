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
                .name("Test product")
                .description("Random desc")
                .price(15)
                .currency(Currency.getInstance("EUR"))
                .build();

        ProductDetails actualProductDetails = productMapper.productToProductDetails(product);

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

    @Test
    void updateProductWithProductDetails_MapsFieldsToExistingObject() {
        ProductDetails updateDetails = ProductDetails.builder()
                .name("Updated product")
                .description("Updated description")
                .price(0.0)
                .currency(Currency.getInstance("EUR"))
                .build();

        Product product = Product.builder()
                .productId(1L)
                .price(15)
                .currency(Currency.getInstance("USD"))
                .build();

        Product expectedProduct = Product.builder()
                .productId(1L)
                .name("Updated product")
                .description("Updated description")
                .price(15)
                .currency(Currency.getInstance("EUR"))
                .build();

        Product actualProduct = productMapper.updateDetailsToProduct(updateDetails, product);

        assertEquals(actualProduct.getProductId(), expectedProduct.getProductId());
        assertEquals(actualProduct.getName(), expectedProduct.getName());
        assertEquals(actualProduct.getDescription(), expectedProduct.getDescription());
        assertEquals(actualProduct.getPrice(), expectedProduct.getPrice());
        assertEquals(actualProduct.getCurrency(), expectedProduct.getCurrency());
    }

    @Test
    void UpdateProductWithNullDetails_DoesNotMapExistingProduct() {
        Product product = Product.builder()
                .productId(1L)
                .name("Test product")
                .description("Test description")
                .price(33)
                .currency(Currency.getInstance("USD"))
                .build();

        ProductDetails updateDetails = ProductDetails.builder().build();

        Product actualProduct = productMapper.updateDetailsToProduct(updateDetails, product);

        assertEquals(actualProduct.getProductId(), product.getProductId());
        assertEquals(actualProduct.getName(), product.getName());
        assertEquals(actualProduct.getDescription(), product.getDescription());
        assertEquals(actualProduct.getPrice(), product.getPrice());
        assertEquals(actualProduct.getCurrency(), product.getCurrency());
    }
}
