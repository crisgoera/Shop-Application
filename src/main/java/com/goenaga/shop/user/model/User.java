package com.goenaga.shop.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.goenaga.shop.security.model.TokenEntity;
import com.goenaga.shop.user.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)

@Table(name = "app_user")
public class User implements UserDetails {
    @Id
    @Column (name = "id", nullable = false)
    private UUID id;
    @Column (nullable = false)
    private String email;

    @Column (nullable = false)
    private String password;

    private String firstName;
    private String lastName;

    @CreatedDate
    @Column (nullable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column (nullable = false)
    private Date lastModified;

    @Column (nullable = false)
    private Role role;

    @OneToOne (mappedBy = "user",cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private TokenEntity token;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }
}
