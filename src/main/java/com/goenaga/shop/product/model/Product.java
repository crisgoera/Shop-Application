package com.goenaga.shop.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.Currency;

@Builder
@Jacksonized
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String productId;

    @Column(unique = true)
    @NonNull
    private String name;

    private String description;

    @NonNull
    private double price;

    private Currency currency;
}
