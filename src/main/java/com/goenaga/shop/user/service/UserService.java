package com.goenaga.shop.user.service;

import com.goenaga.shop.auth.model.SignupRequest;
import com.goenaga.shop.security.service.JWTService;
import com.goenaga.shop.user.enums.Role;
import com.goenaga.shop.user.mapper.UserMapper;
import com.goenaga.shop.user.model.User;
import com.goenaga.shop.user.model.UserDTO;
import com.goenaga.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final UserMapper userMapper;

    public String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public User createNewUser(SignupRequest request) {
        Date timestamp = new Date();
        return User.builder()
                .id(UUID.randomUUID())
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

    public Optional<User> findUserByEmail(String email) { return userRepository.findUserByEmail(email); }

    public UserDTO getUserFromToken(String token) {
        User user = userRepository.findUserByEmail(jwtService.getEmail(token)).get();
        return userMapper.mapUserToDTO(user);
    }

    public UserDTO updateUserProfile(String token, UserDTO updateDetails) {
        User user = userRepository.findUserByEmail(jwtService.getEmail(token)).get();
        User updatedUser = userMapper.updateUserDetails(user, updateDetails);
        userRepository.save(updatedUser);
        return userMapper.mapUserToDTO(updatedUser);
    }
}
