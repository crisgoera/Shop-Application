package com.goenaga.shop.security.repository;

import com.goenaga.shop.security.model.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository("tokenRepository")
public interface TokenRepository extends CrudRepository<TokenEntity, String> {
    Optional<TokenEntity> findByUserId(UUID user_Id);
    void deleteByUserId(UUID user_Id);
}
