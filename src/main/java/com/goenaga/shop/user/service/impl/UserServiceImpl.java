package com.goenaga.shop.user.service.impl;

import com.goenaga.shop.auth.model.SignupRequest;
import com.goenaga.shop.security.service.JWTService;
import com.goenaga.shop.user.enums.Role;
import com.goenaga.shop.user.exception.UserNotFoundException;
import com.goenaga.shop.user.mapper.UserMapper;
import com.goenaga.shop.user.model.User;
import com.goenaga.shop.user.model.UserDTO;
import com.goenaga.shop.user.repository.UserRepository;
import com.goenaga.shop.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final UserMapper userMapper;

    public String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public User createNewUser(SignupRequest request) {
        Date timestamp = new Date();
        return User.builder()
                .email(request.getEmail())
                .password(encodePassword(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .createdAt(timestamp)
                .lastModified(timestamp)
                .role(Role.USER)
                .build();
    }

    public void save(User user) { userRepository.save(user); }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public UserDTO getUserFromToken(String token) {
        String userEmail = jwtService.getEmail(token);
        Optional<User> foundUser = userRepository.findUserByEmail(userEmail);
        boolean exists = foundUser.isPresent();

        if (!exists) {
            throw new UserNotFoundException();
        }

        return userMapper.mapUserToDTO(foundUser.get());
    }

    public UserDTO updateUserProfile(String token, UserDTO updateDetails) {
        String userEmail = jwtService.getEmail(token);
        Optional<User> foundUser = userRepository.findUserByEmail(userEmail);
        boolean exists = foundUser.isPresent();

        if (!exists) {
            throw new UserNotFoundException();
        }

        User updatedUser = userMapper.updateUserDetails(foundUser.get(), updateDetails);
        userRepository.save(updatedUser);

        return userMapper.mapUserToDTO(updatedUser);
    }
}
