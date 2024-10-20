package com.goenaga.shop.security.repository;

import com.goenaga.shop.security.model.TokenEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface TokenRepository extends MongoRepository<TokenEntity, String> {
    @Query("{'email': ?0}")
    Optional<TokenEntity> findTokenByEmail(String email);

    void deleteByEmail(String email);
}
