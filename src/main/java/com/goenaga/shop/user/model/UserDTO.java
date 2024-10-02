package com.goenaga.shop.user.model;

import com.goenaga.shop.user.enums.Role;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class UserDTO {
    private final String password;
    private final String firstName;
    private final String lastName;
    private final Role role;
}
