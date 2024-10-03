package com.goenaga.shop.security.service;

import com.goenaga.shop.security.model.TokenEntity;
import com.goenaga.shop.security.repository.TokenRepository;
import com.goenaga.shop.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class JWTService {
    @Value("${application.security.jwt.encryption_key}")
    private String SECRET_KEY;
    private String TOKEN_HEADER = "Authorization";
    private String TOKEN_PREFIX = "Bearer ";
    private final TokenRepository tokenRepository;

    public String createToken(User user) {
//        Delete previous issued tokens to the user
        tokenRepository.deleteByEmail(user.getEmail());

//        Issue new token
        String jwtToken = issueToken(user.getEmail());
        tokenRepository.save(TokenEntity.builder().token(jwtToken).email(user.getEmail()).build());
        return jwtToken;
    }

    public String refreshToken(String token) {
//        Authenticate token
        String email = getClaims(token).getSubject();

//        Delete previous tokens associated with user and issue new token
        tokenRepository.deleteByEmail(email);
        String newToken = issueToken(email);
        tokenRepository.save(TokenEntity.builder().token(newToken).email(email).build());

        return newToken;
    }

//    Retrieve secret key.
    private SecretKey getEncKey() {
        byte[] encBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(encBytes);
    }

//    Issue new token instance
    private String issueToken(String email) {
        Date timestamp = new Date();
        Date expiration = new Date(timestamp.getTime() + 24*60*60*1000); // Timestamp + 24 hours

        return  TOKEN_PREFIX + Jwts.builder()
                .issuer("authService")
                .subject(email)
                .issuedAt(timestamp)
                .expiration(expiration)
                .signWith(getEncKey())
                .compact();
    }

//    Parse token and retrieve claims
    public Claims getClaims(String jwtToken) {
        try {
            return Jwts.parser()
                    .verifyWith(getEncKey())
                    .build()
                    .parseSignedClaims(jwtToken)
                    .getPayload();
        } catch (JwtException exception) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject();
    }

    public Date getExpiration(String token) {
        return getClaims(token).getExpiration();
    }

    private boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String tokenEmail = getUsername(token);
        return (tokenEmail.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
