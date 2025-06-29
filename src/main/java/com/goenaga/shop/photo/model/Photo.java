package com.goenaga.shop.photo.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Entity
@Getter
@Table(name = "photos")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private long photoId;

    @Column(name = "photo_name")
    private String photoName;

    @Column(name = "photo_url")
    private String photoUrl;
}
