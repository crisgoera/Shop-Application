package com.goenaga.shop.product.service;

import com.goenaga.shop.product.model.NewProductRequest;
import com.goenaga.shop.product.model.Product;
import com.goenaga.shop.product.model.ProductDetails;

import java.util.List;

public interface ProductService {
    List<ProductDetails> getProducts();
    ProductDetails createNewProduct(NewProductRequest productRequest);
    Product getProductEntityById(int id);
    ProductDetails getProductDetailsById(int id);
    ProductDetails updateProduct(int id, ProductDetails updateDetails);
    void removeProduct(int id);
    Product save(Product product);
}
