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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Document("users")
public class User implements UserDetails{
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
