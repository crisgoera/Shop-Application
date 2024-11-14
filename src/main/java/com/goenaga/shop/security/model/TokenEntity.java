package com.goenaga.shop.security.model;

import com.goenaga.shop.user.model.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

@Table(name = "tokens")
public class TokenEntity {
    @Id
    @Column(name = "user_email", nullable = false)
    private String email;

    @Column(nullable = false)
    private String token;
}
