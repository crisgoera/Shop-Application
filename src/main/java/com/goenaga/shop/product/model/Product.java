package com.goenaga.shop.product.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Getter
@Document("products")
public class Product {
    private final int productId;
    private final String name;
    private final String description;
    private final float price;
    private final List<String> imgUrls;
}
