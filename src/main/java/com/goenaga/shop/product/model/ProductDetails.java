package com.goenaga.shop.product.model;

import lombok.*;

import java.util.Currency;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails {
    private Long productId;
    private String name;
    private String description;
    private double price;
    private Currency currency;
}
