package com.goenaga.shop.photo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Entity
public class Photo {
    @Id
    private long photoId;
    private String title;

}
