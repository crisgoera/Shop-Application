package com.goenaga.shop.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tokens")
@Builder
@Getter
public class TokenEntity {
    @Id
    @NonNull
    private final String email;
    @NonNull
    private final String token;
}
