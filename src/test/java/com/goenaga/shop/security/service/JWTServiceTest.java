package com.goenaga.shop.security.service;

import com.goenaga.shop.security.model.TokenEntity;
import com.goenaga.shop.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.SignatureException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application.properties")
@SpringBootTest
class JWTServiceTest {
    @InjectMocks
    private JWTService jwtService;

    @Value("application.security.jwt.encryption_key")
    private String secretKey;

    private final User mockUser = User.builder().email("test@gmail.com").build();

    @BeforeEach
    void setup() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        String secretKey = properties.getProperty("application.security.jwt.encryption_key");

        // Asigna la clave secreta al servicio
        jwtService.setSecretKey(secretKey);
    }

    @Test
    void issueToken_Returns_ConstructedJWTToken() {
        assertNotNull(secretKey, "La clave secreta no fue cargada.");

        Assertions.assertThat(jwtService.issueToken(mockUser)).isNotNull();
    }

    @Test
    void createTokenEntity_Returns_TokenEntityInstance() {
        var tokenEntity = jwtService.createTokenEntity(mockUser);

        Assertions.assertThat(tokenEntity).isInstanceOf(TokenEntity.class);
//        Checks jwtToken generation
        Assertions.assertThat(tokenEntity.getToken()).isNotNull();
    }

    @Test
    void refreshToken_Returns_NewTokenString() {
        String token = jwtService.refreshToken(mockUser);

        Assertions.assertThat(token).isNotNull();
        Assertions.assertThat(token).isInstanceOf(String.class);
    }

    @Test
    void parseClaims_Returns_ClaimsWithValidToken() {
        Claims claims = jwtService.parseClaims(jwtService.issueToken(mockUser));
        Assertions.assertThat(claims).isNotNull();
        Assertions.assertThat(claims).isInstanceOf(Claims.class);
    }


    @Test
    void parseClaims_Returns_CorrectUser_Info() {
        User user = User.builder().email("test@mail.com").build();
        Claims claims = jwtService.parseClaims(jwtService.issueToken(user));

        Assertions.assertThat(claims.getSubject()).isNotNull().isEqualTo("test@mail.com");
    }

    @Test
    void parseClaims_Throws_SignatureException_OnUnvalidatedToken() {
        String tamperedToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MDIyfQ.cThIIoDvwdueQB468K5xDc5633seEFoqwxjF_xSJyQQ";

        Exception exception = assertThrows(SignatureException.class, () -> jwtService.parseClaims(tamperedToken));

        String expectedMessage = "JWT validity cannot be asserted";
        String message = exception.getMessage();

        assertTrue(message.contains(expectedMessage));
    }

    @Test
    void parseClaims_ReturnsClaims_If_TokenExpired() {
        String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhdXRoU2VydmljZSIsInN1YiI6ImFkbWluQGFkbWluLmNvbSIsImlhdCI6MTczMzk0MzE5MiwiZXhwIjoxNzMzOTQzMjUyfQ.Nmwif2zfMcyyz6ScsAqvw3BINk-CQRXeeHRnkKbXCeDIPO5H3k_r2miqhL5l4ILxegtA-h1ODyAkmD6W_-W4oQ";

        Claims claims = jwtService.parseClaims(expiredToken);

        Assertions.assertThat(claims).isNotNull().isInstanceOf(Claims.class);
    }

    @Test
    void getEmail_Returns_UserEmail_FromToken() {
        String token = jwtService.issueToken(mockUser);

        Assertions.assertThat(jwtService.getEmail(token)).isNotNull().isEqualTo(mockUser.getEmail());
    }

    @Test
    void isTokenExpired() {
        String token = jwtService.issueToken(mockUser);
        String expiredToken = "eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJhdXRoU2VydmljZSIsInN1YiI6ImFkbWluQGFkbWluLmNvbSIsImlhdCI6MTczMzk0MzE5MiwiZXhwIjoxNzMzOTQzMjUyfQ.Nmwif2zfMcyyz6ScsAqvw3BINk-CQRXeeHRnkKbXCeDIPO5H3k_r2miqhL5l4ILxegtA-h1ODyAkmD6W_-W4oQ";

        Assertions.assertThat(jwtService.isTokenExpired(token)).isInstanceOf(Boolean.class).isEqualTo(false);
        Assertions.assertThat(jwtService.isTokenExpired(expiredToken)).isInstanceOf(Boolean.class).isEqualTo(true);
    }
}