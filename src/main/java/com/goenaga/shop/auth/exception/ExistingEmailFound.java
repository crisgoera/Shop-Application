package com.goenaga.shop.auth.exception;

public class ExistingEmailFound extends AuthenticationException {
    public ExistingEmailFound() {
        super("Email already in use");
    }
}
