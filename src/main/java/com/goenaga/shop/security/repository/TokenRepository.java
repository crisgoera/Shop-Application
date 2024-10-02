package com.goenaga.shop.security.repository;

import com.goenaga.shop.security.model.TokenEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface TokenRepository extends MongoRepository<TokenEntity, String> {
    @Query("{'email': ?0}")
    List<TokenEntity> findTokenByEmail(String email);

    void deleteByEmail(String email);
}
