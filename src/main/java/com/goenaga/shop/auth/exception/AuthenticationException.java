package com.goenaga.shop.auth.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends Exception {
    private HttpStatus httpStatus;
    private String error;

    public AuthenticationException(HttpStatus httpStatus, String error) {
        super();
        this.httpStatus = httpStatus;
        this.error = error;
    }
}
