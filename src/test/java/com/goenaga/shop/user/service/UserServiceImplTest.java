package com.goenaga.shop.user.service;

import com.goenaga.shop.auth.model.SignupRequest;
import com.goenaga.shop.security.service.JWTService;
import com.goenaga.shop.user.enums.Role;
import com.goenaga.shop.user.exception.UserNotFoundException;
import com.goenaga.shop.user.mapper.UserMapper;
import com.goenaga.shop.user.model.User;
import com.goenaga.shop.user.model.UserDTO;
import com.goenaga.shop.user.repository.UserRepository;
import com.goenaga.shop.user.service.impl.UserServiceImpl;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

//TODO:
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTService jwtService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private final User mockedUser = User.builder().email("test@mail").password("test").createdAt(new Date()).lastModified(new Date()).role(Role.USER).build();
    private final SignupRequest mockedRequest = SignupRequest.builder().email("test@mail.com").firstName("testUser").lastName("testLastName").password("testPassword").build();
    private final UserDTO mockedUserDTO = UserDTO.builder().email("test@mail.com").build();
    @Test
    void userService_CreateNewUser_ReturnsCorrectUserInstance() {
        assertThat(userService.createNewUser(mockedRequest)).isNotNull().isInstanceOf(User.class);
    }

    @Test
    void findUserByEmail_ReturnsOptionalOfUser() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(mockedUser));

        assertThat(userService.findUserByEmail("test@mail.com")).isNotNull();
        assertThat(userService.findUserByEmail("test@mail.com").get()).isInstanceOf(User.class);
    }

    @Test
    void getUserFromToken_Returns_OptionalOfUserDTOWhenUserIsFound() {
        when(jwtService.getEmail(anyString())).thenReturn("test@mail.com");
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(mockedUser));
        when(userMapper.mapUserToDTO(mockedUser)).thenReturn(mockedUserDTO);

        assertThat(userService.getUserFromToken("string")).isNotNull().isInstanceOf(UserDTO.class);
    }

    @Test
    void getUserFromToken_ThrowsNotFoundExceptionWhenUserDoesIsNotFound() {
        when(jwtService.getEmail(anyString())).thenReturn("test@mail.com");
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()-> userService.getUserFromToken("string"));

        verify(userMapper, never()).mapUserToDTO(any(User.class));
    }

//    TODO: Finish the test
    @Test
    void updateUserProfile() {
    }
}