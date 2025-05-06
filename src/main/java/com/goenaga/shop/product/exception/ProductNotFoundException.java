package com.goenaga.shop.product.exception;

public class ProductNotFoundException extends ProductException {
    public ProductNotFoundException() {
        super("Product not found");
    }
}
