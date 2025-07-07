package com.goenaga.shop.security.model;

import com.goenaga.shop.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter

@Table(name = "token")
public class TokenEntity {
    @Id
    @Column(name = "user_id",nullable = false)
    private UUID user_Id;

    @Column(nullable = false)
    private String token;

    @OneToOne
    @MapsId
    @JoinColumn (name = "user_id")
    private User user;
}
