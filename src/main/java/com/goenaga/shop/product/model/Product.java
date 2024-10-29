package com.goenaga.shop.product.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Currency;
import java.util.List;

@Builder
@Jacksonized
@Getter
@Setter
@Document("products")
public class Product {
    @Id
    private final String productId;
    @Indexed(unique = true)
    @NonNull
    private String name;
    private String description;
    @NonNull
    private float price;
    private Currency currency;
    private List<Photo> photos;
}
