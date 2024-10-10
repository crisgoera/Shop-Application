package com.goenaga.shop.product.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Getter
@Setter
@Document("products")
public class Product {
    @Id
    private final int productId;
    @Indexed(unique = true)
    @NonNull
    private final String name;
    private final String description;
    @NonNull
    private final float price;
    private final List<String> imgUrls;
    private final int stock;
    private final boolean isAvailable;
    private final int unitsSold;
}
