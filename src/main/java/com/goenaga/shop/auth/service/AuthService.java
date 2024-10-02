package com.goenaga.shop.auth.service;

import com.goenaga.shop.auth.error.ErrorResponse;
import com.goenaga.shop.auth.model.AuthResponse;
import com.goenaga.shop.auth.model.LoginRequest;
import com.goenaga.shop.auth.model.SignupRequest;
import com.goenaga.shop.security.service.JWTService;
import com.goenaga.shop.user.enums.Role;
import com.goenaga.shop.user.model.User;
import com.goenaga.shop.user.repository.UserRepository;
import com.goenaga.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

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
        try {
            String email = request.getEmail();
            User user = User.builder().email(email).build();
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, request.getPassword())
            );

            return ResponseEntity.ok(AuthResponse.builder().email(email).token(jwtService.createToken(user)).build());

        } catch (BadCredentialsException e){
            ErrorResponse error = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Invalid username or password")
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
