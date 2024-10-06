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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JWTService jwtService;

    public AuthResponse signUp(SignupRequest request) {
        User newUser = userService.createNewUser(request);

//        Issue new JWT token
        String jwtToken = jwtService.createToken(newUser);
        return AuthResponse.builder()
                .email(newUser.getEmail())
                .token(jwtToken)
                .build();
    }

    public ResponseEntity login(LoginRequest request) {
            User user = userService.findUserByEmail(request.getEmail()).get();
            String saltedPassword = user.getPassword();

            if (!validateLoginCredentials(request.getPassword(), saltedPassword)) {
                ErrorResponse error = ErrorResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED)
                        .message("Invalid username or password")
                        .build();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }

            return ResponseEntity.ok(AuthResponse.builder().email(request.getEmail()).token(jwtService.createToken(user)).build());
    }

    private boolean validateLoginCredentials(String plainPassword, String saltedPassword) {
        return new BCryptPasswordEncoder().matches(plainPassword, saltedPassword);
    }
}
