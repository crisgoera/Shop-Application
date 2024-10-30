package com.goenaga.shop.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;


@Builder
@Jacksonized
@Getter
public class UserDTO {
    private final String email;
    private final String firstName;
    private final String lastName;
}
