package com.goenaga.shop.auth.service;

import com.goenaga.shop.auth.model.AuthResponse;
import com.goenaga.shop.auth.model.LoginRequest;
import com.goenaga.shop.auth.model.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<AuthResponse> userSignUp(SignupRequest signupRequest);

    ResponseEntity<AuthResponse> userLogin(LoginRequest request);
}
