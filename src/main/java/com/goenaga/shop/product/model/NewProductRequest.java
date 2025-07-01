package com.goenaga.shop.product.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Currency;

@Builder
@Getter
public class NewProductRequest {
    @NonNull
    private String name;
    private String description;
    private double price;
    @NonNull
    private Currency currency;
}
