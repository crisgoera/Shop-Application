package com.goenaga.shop.user.exception;

public class DuplicatedUserException extends UserException {
    public DuplicatedUserException() {
        super("User already exists");
    }
}
