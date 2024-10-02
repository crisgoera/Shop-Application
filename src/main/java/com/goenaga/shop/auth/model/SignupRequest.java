package com.goenaga.shop.auth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignupRequest {
    private final String email;
    private final String password;
    private final String firstName;
    private final String lastName;
}
