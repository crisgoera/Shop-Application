package com.goenaga.shop.product.repository;

import com.goenaga.shop.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{íd}: ?0")
    Optional<Product> findProductById(int id);
}
