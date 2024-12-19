package com.goenaga.shop.product.mapper;

import com.goenaga.shop.product.model.NewProductRequest;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.model.ProductDetails;
import org.mapstruct.*;

@Mapper(componentModel = "spring" ,nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    Product productDetailsToProduct(ProductDetails productDetails);
    ProductDetails productToProductDetails(Product product);
    Product newProductRequestToProduct(NewProductRequest newProduct);

    @BeforeMapping
    default void beforeUpdate(ProductDetails updateDetails, @MappingTarget Product product) {
        if (updateDetails.getPrice() == 0.0) {
            updateDetails.setPrice(product.getPrice());
        }
    }
    Product updateDetailsToProduct(ProductDetails updateDetails, @MappingTarget Product product);

}
