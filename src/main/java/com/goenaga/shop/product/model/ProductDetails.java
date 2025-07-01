package com.goenaga.shop.product.model;

import com.goenaga.shop.photo.model.Photo;
import lombok.*;

import java.util.Currency;
import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails {
    private String name;
    private String description;
    private double price;
    private Currency currency;
    private List<Photo> photoList;
}
