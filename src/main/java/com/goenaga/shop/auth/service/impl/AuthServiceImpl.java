package com.goenaga.shop.auth.service.impl;

import com.goenaga.shop.auth.exception.ExistingEmailFound;
import com.goenaga.shop.auth.exception.InvalidCredentialsException;
import com.goenaga.shop.auth.model.AuthResponse;
import com.goenaga.shop.auth.model.LoginRequest;
import com.goenaga.shop.auth.model.SignupRequest;
import com.goenaga.shop.auth.exception.AuthenticationException;
import com.goenaga.shop.auth.service.AuthService;
import com.goenaga.shop.security.model.TokenEntity;
import com.goenaga.shop.security.service.JWTService;
import com.goenaga.shop.user.model.User;
import com.goenaga.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JWTService jwtService;
    private final String TOKEN_PREFIX = "Bearer ";

    public ResponseEntity<AuthResponse> userSignUp(SignupRequest request) throws AuthenticationException {
        if (userService.findUserByEmail(request.getEmail()).isPresent()) {
            throw new ExistingEmailFound();
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

    public ResponseEntity<AuthResponse> userLogin(LoginRequest request) throws AuthenticationException {
        Optional<User> userOptional = userService.findUserByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException();
        }

        User user = userOptional.get();

        if (!validateLoginCredentials(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
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
}
