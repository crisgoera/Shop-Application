package com.goenaga.shop.product.mapper;

import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.model.ProductDetails;
import org.mapstruct.Mapper;



@Mapper
public interface ProductMapper {
    Product productDetailsToProduct(ProductDetails productDetails);
    ProductDetails productToProductDetails(Product product);
}
