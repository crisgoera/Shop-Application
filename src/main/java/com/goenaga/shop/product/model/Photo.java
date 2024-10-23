package com.goenaga.shop.product.model;

import lombok.Builder;
import org.bson.types.Binary;

@Builder
public class Photo {
    private String title;
    private Binary image;
}
