package com.goenaga.shop.user.model;

import com.goenaga.shop.user.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;
import java.util.UUID;

@Builder
@Getter
@Document("users")
public class User {
    @Id
    private final UUID id;
    @Indexed (unique = true)
    @NonNull
    private final String email;
    @NonNull
    private final String password;
    private final String firstName;
    private final String lastName;
    @CreatedDate
    private final Date createdAt;
    @LastModifiedDate
    private final Date lastModified;
    private final Role role;
}
