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

    @Column(name = "product_id", nullable = false)
    private int productId;

    @Column(name = "photo_name")
    private String photoName;

    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
}
