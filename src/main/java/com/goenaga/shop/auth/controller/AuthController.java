package com.goenaga.shop.auth.controller;

import com.goenaga.shop.auth.model.AuthResponse;
import com.goenaga.shop.auth.model.LoginRequest;
import com.goenaga.shop.auth.model.SignupRequest;
import com.goenaga.shop.auth.service.AuthService;
import com.goenaga.shop.auth.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping ("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping ("/signup")
    public ResponseEntity<AuthResponse> signUp(@RequestBody SignupRequest signupRequest) throws AuthenticationException {
        return authService.userSignUp(signupRequest);
    }

    @PostMapping ("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request ) throws AuthenticationException {
        return authService.userLogin(request);
    }
}
