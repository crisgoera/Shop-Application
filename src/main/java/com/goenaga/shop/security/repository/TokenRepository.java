package com.goenaga.shop.security.repository;

import com.goenaga.shop.security.model.TokenEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("tokenRepository")
public interface TokenRepository extends CrudRepository<TokenEntity, String> {
}
