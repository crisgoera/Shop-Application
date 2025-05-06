package com.goenaga.shop.product.exception;

public class DuplicatedProductException extends ProductException {
    public DuplicatedProductException() {
        super("Product already exists");
    }
}
