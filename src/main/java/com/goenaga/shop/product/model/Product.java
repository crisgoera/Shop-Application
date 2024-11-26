package com.goenaga.shop.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.Currency;

@Builder
@Jacksonized
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

@Table (name = "product")
public class Product {
    @Id
    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Currency currency;
}
