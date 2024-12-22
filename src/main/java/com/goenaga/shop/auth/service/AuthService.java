package com.goenaga.shop.auth.service;

import com.goenaga.shop.error.model.ErrorResponse;
import com.goenaga.shop.auth.model.AuthResponse;
import com.goenaga.shop.auth.model.LoginRequest;
import com.goenaga.shop.auth.model.SignupRequest;
import com.goenaga.shop.security.model.TokenEntity;
import com.goenaga.shop.security.service.JWTService;
import com.goenaga.shop.user.model.User;
import com.goenaga.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JWTService jwtService;
    private final String TOKEN_PREFIX = "Bearer ";

    public ResponseEntity userSignUp(SignupRequest request) {
        if (userService.findUserByEmail(request.getEmail()).isPresent()) {
            ErrorResponse error = ErrorResponse.builder()
                    .message("Email already in use")
                    .build();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
        }

        User newUser = userService.createNewUser(request);
        TokenEntity tokenEntity = jwtService.createTokenEntity(newUser);
        newUser.setTokenEntity(tokenEntity);
        userService.save(newUser);

        return ResponseEntity.ok(AuthResponse.builder()
                        .token(TOKEN_PREFIX + tokenEntity.getToken())
                        .build()
        );
    }

    public ResponseEntity userLogin(LoginRequest request) {
        Optional<User> userOptional = userService.findUserByEmail(request.getEmail());
        if (userOptional.isEmpty()) { return unauthorizedResponse(); }

        User user = userOptional.get();

        if (!validateLoginCredentials(request.getPassword(), user.getPassword())) {
            return unauthorizedResponse();
        }

        TokenEntity tokenEntity = jwtService.createTokenEntity(user);
        jwtService.save(tokenEntity);

        return ResponseEntity.ok(AuthResponse.builder()
                .token(TOKEN_PREFIX + tokenEntity.getToken())
                .build());
    }

    private boolean validateLoginCredentials(String plainPassword, String saltedPassword) {
        return new BCryptPasswordEncoder().matches(plainPassword, saltedPassword);
    }

    private ResponseEntity<ErrorResponse> unauthorizedResponse() {
        ErrorResponse error = ErrorResponse.builder()
                .message("Invalid username or password")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
