package com.goenaga.shop.auth.controller;

import com.goenaga.shop.auth.model.LoginRequest;
import com.goenaga.shop.auth.model.SignupRequest;
import com.goenaga.shop.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping ("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping ("/signup")
    public ResponseEntity signUp(@RequestBody SignupRequest signupRequest) {
        return authService.userSignUp(signupRequest);
    }

    @PostMapping ("/login")
    public ResponseEntity login(@RequestBody LoginRequest request ) {
        return authService.userLogin(request);
    }
}
