package com.goenaga.shop.product.repository;

import com.goenaga.shop.product.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Override
    List<Product> findAll();
    Optional<Product> findByName(String name);
}
