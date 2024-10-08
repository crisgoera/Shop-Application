package com.goenaga.shop.auth.service;

import com.goenaga.shop.auth.error.ErrorResponse;
import com.goenaga.shop.auth.model.AuthResponse;
import com.goenaga.shop.auth.model.LoginRequest;
import com.goenaga.shop.auth.model.SignupRequest;
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

    public ResponseEntity signUp(SignupRequest request) {
        Optional<User> newUserOptional = userService.createNewUser(request);
        if (newUserOptional.isEmpty()) {
            ErrorResponse error = ErrorResponse.builder()
                    .status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .message("Email already in use")
                    .build();

            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
        }

        User newUser = newUserOptional.get();

//        Issue new JWT token and return response
        return ResponseEntity.ok(AuthResponse.builder()
                .token(jwtService.createToken(newUser))
                .build());
    }

    public ResponseEntity login(LoginRequest request) {
        Optional<User> userOptional = userService.findUserByEmail(request.getEmail());
        if (userOptional.isEmpty()) { return unauthorizedResponse(); }

        User user = userOptional.get();

        if (!validateLoginCredentials(request.getPassword(), user.getPassword())) {
            return unauthorizedResponse();
        }

        return ResponseEntity.ok(AuthResponse.builder()
                .token(jwtService.createToken(user))
                .build());
    }

    private boolean validateLoginCredentials(String plainPassword, String saltedPassword) {
        return new BCryptPasswordEncoder().matches(plainPassword, saltedPassword);
    }

    private ResponseEntity<ErrorResponse> unauthorizedResponse() {
        ErrorResponse error = ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("Invalid username or password")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
