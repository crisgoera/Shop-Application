package com.goenaga.shop.product.model;

import com.goenaga.shop.photo.model.Photo;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.Currency;
import java.util.Set;

@Builder
@Jacksonized
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false, updatable = false)
    private int productId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Currency currency;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Photo> photoList;
}
