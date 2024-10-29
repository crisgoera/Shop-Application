package com.goenaga.shop.product.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Currency;

@Builder
@Getter
public class NewProductDTO {
    @NonNull
    private String name;
    private String description;
    @NonNull
    private double price;
    private Currency currency;
    private int stock;
    private MultipartFile[] images;
    private String[] imageTitles;
}
