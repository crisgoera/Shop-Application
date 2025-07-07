package com.goenaga.shop.product.model;

import com.goenaga.shop.photo.model.Photo;
import lombok.*;

import java.util.Currency;
import java.util.Set;

@Builder
@Data
public class ProductDetails {
    private String name;
    private String description;
    private double price;
    private Currency currency;
    private Set<Photo> photoList;
}
