package com.goenaga.shop.auth.error;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ErrorResponse {
    private final HttpStatus status;
    private final String message;
}
