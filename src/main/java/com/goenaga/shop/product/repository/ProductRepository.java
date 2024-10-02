package com.goenaga.shop.product.repository;

import com.goenaga.shop.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
