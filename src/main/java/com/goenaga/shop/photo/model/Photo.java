package com.goenaga.shop.photo.model;

import com.goenaga.shop.product.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "photos")
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @Id
    @Column(name="photo_id", nullable = false)
    private String photoId;

    @Column(name = "photo_name")
    private String photoName;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
