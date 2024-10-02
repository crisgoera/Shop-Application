package com.goenaga.shop.auth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Getter
public class LoginRequest {
    private final String email;
    private final String password;
}
