package com.goenaga.shop.product.repository;

import com.goenaga.shop.product.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
    Product findTopByOrderByProductIdDesc();
}
