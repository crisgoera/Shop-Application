package com.goenaga.shop.security.service;

import com.goenaga.shop.security.model.TokenEntity;
import com.goenaga.shop.security.repository.TokenRepository;
import com.goenaga.shop.user.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.ServletException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class JWTService {
    @Value("${application.security.jwt.encryption_key}")
    private String SECRET_KEY;
    private String TOKEN_HEADER = "Authorization";
    private String TOKEN_PREFIX = "Bearer ";
    private int TOKEN_EXPIRATION = 1000*60; // Expiration time in ms;
    private final TokenRepository tokenRepository;

    public String createToken(User user) {
//        Delete previous issued tokens to the user
        tokenRepository.deleteByEmail(user.getEmail());

//        Issue new token
        String jwtToken = issueToken(user.getEmail());
        tokenRepository.save(TokenEntity.builder().token(jwtToken).email(user.getEmail()).build());
        return TOKEN_PREFIX + jwtToken;
    }

    public String refreshToken(String token) throws ServletException {
//        Authenticate token and retrieve email
        String email = parseClaims(token).getSubject();
//        Check if expired token is in DB.
        String lastIssuedToken = tokenRepository.findTokenByEmail(email).getToken();

//        If provided expired token equals last issued token, issue a new token
        if (Objects.equals(token, lastIssuedToken)) {
            String newToken = issueToken(email);
//            Delete previous token
            tokenRepository.deleteByEmail(email);
//            Save new token
            tokenRepository.save(TokenEntity.builder().token(newToken).email(email).build());

            return newToken;
        } else {
            throw new ServletException("Revoked access. Token no longer valid");
        }
    }

//    Retrieve secret key.
    private SecretKey getEncKey() {
        byte[] encBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(encBytes);
    }

//    Issue new token instance
    private String issueToken(String email) {
        Date timestamp = new Date();
        Date expiration = new Date(timestamp.getTime() + TOKEN_EXPIRATION);

        return  Jwts.builder()
                    .issuer("authService")
                    .subject(email)
                    .issuedAt(timestamp)
                    .expiration(expiration)
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
