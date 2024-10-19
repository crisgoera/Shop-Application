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
    private String name;
    private String description;
    @NonNull
    private float price;
    private List<String> imgUrls;
    private int stock;
    private boolean isAvailable;
    private  int unitsSold;


    public void setIsAvailable() {
        this.isAvailable = stock > 0;
    }
}
