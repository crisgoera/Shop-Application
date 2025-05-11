package com.goenaga.shop.security.service;

import com.goenaga.shop.security.model.TokenEntity;
import com.goenaga.shop.user.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
    TokenEntity createTokenEntity(User newUser);

    void save(TokenEntity tokenEntity);

    String getEmail(String token);

    boolean isTokenExpired(String token);

    String refreshToken(User user);

    boolean validateToken(String token, UserDetails userDetails);
}
