package com.goenaga.shop.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.Currency;
import java.util.List;

@Builder
@Jacksonized
@Getter
@Setter
@Entity
public class Product {
    @Id
    private final String productId;
    @Column(unique = true)
    @NonNull
    private String name;
    private String description;
    @NonNull
    private double price;
    private Currency currency;
}
