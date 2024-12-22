package com.goenaga.shop.security.service;

import com.goenaga.shop.security.model.TokenEntity;
import com.goenaga.shop.security.repository.TokenRepository;
import com.goenaga.shop.user.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.*;


@Service
@RequiredArgsConstructor
public class JWTService {
    public void setSecretKey(String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    @Value("${application.security.jwt.encryption_key}")
    private String SECRET_KEY;
    private final int TOKEN_EXPIRATION = 1000*60; // Expiration time in ms;
    @Autowired
    private TokenRepository tokenRepository;

    @Transactional
    public TokenEntity createTokenEntity(User user) {
//        Issue new token
        String jwtToken = issueToken(user);
        return TokenEntity.builder()
                .user_Id(user.getId())
                .token(jwtToken)
                .user(user)
                .build();
    }

    public String refreshToken(User user) {
//        If provided expired token equals last issued token, issue a new token
        String newToken = issueToken(user);

//        Update DB entry with the new token
        tokenRepository.save(TokenEntity.builder().token(newToken).user_Id(user.getId()).build());
        return newToken;
    }

    public void save(TokenEntity entity) { tokenRepository.save(entity); }

//    Retrieve secret key.
    private SecretKey getEncKey() {
        byte[] encBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(encBytes);
    }

//    Issue new token instance
    public String issueToken(User user) {
        Instant timestamp = Instant.now();
        Instant expiration = timestamp.plusMillis(TOKEN_EXPIRATION);

        return  Jwts.builder()
                    .issuer("authService")
                    .subject(user.getEmail())
                    .issuedAt(Date.from(timestamp))
                    .expiration(Date.from(expiration))
                    .signWith(getEncKey())
                    .compact();
    }

//    Parse, verify and retrieve claims
    public Claims parseClaims(String jwtToken) {
        try {
            return Jwts.parser()
                    .verifyWith(getEncKey())
                    .build()
                    .parseSignedClaims(jwtToken)
                    .getPayload();
        } catch (ExpiredJwtException ex) {
            // Return claims if they can be verified but token is expired
            return ex.getClaims();
        } catch (SignatureException se) {
            // Throw exception if claims can't be verified
            throw new SignatureException("JWT validity cannot be asserted");
        }
    }

    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return parseClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String tokenEmail = parseClaims(token).getSubject();

        return (tokenEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
