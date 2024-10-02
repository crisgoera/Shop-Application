package com.goenaga.shop.auth.error;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public class ErrorResponse {
    private final HttpStatus status;
    private final String message;
}
