package com.goenaga.shop.user.service;

import com.goenaga.shop.auth.model.SignupRequest;
import com.goenaga.shop.security.service.JWTService;
import com.goenaga.shop.user.enums.Role;
import com.goenaga.shop.user.model.User;
import com.goenaga.shop.user.repository.UserRepository;
import com.goenaga.shop.user.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

//TODO:
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void userService_CreateUser_ReturnsCorrectUserInstance() {
        SignupRequest request = SignupRequest.builder()
                .email("test@mail.com")
                .firstName("testUser")
                .lastName("testLastName")
                .password("testPassword")
                .build();

        User newUser = userService.createNewUser(request);

        Assertions.assertThat(newUser).isNotNull();
        Assertions.assertThat(newUser.getEmail()).isEqualTo(request.getEmail());
        Assertions.assertThat(newUser.getFirstName()).isEqualTo(request.getFirstName());
        Assertions.assertThat(newUser.getLastName()).isEqualTo(request.getLastName());
        Assertions.assertThat(newUser.getPassword()).isNotEqualTo(request.getPassword());
        Assertions.assertThat(newUser.getCreatedAt()).isNotNull();
        Assertions.assertThat(newUser.getLastModified()).isNotNull();
    }

    @Test
    void getUserFromToken_Returns_OptionalOfUserDTO() {
        User user = User.builder()
                .email("test@user")
                .password("test")
                .createdAt(new Date())
                .lastModified(new Date())
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    @Test
    void updateUserProfile() {
    }
}