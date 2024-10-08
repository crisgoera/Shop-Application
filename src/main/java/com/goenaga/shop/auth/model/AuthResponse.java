package com.goenaga.shop.auth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private final String token;
}
