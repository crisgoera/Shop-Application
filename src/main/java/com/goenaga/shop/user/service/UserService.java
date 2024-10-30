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
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final UserMapper userMapper;

    public String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public Optional<User> createNewUser(SignupRequest request) {
        Optional<User> user = userRepository.findUserByEmail(request.getEmail());
        if (user.isPresent()) { return user.empty(); }

//        Save new user in DB
        Date timestamp = new Date();
        User newUser = User.builder()
                .id(UUID.randomUUID())
                .email(request.getEmail())
                .password(encodePassword(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .createdAt(timestamp)
                .lastModified(timestamp)
                .role(Role.USER)
                .build();
        return Optional.of(userRepository.save(newUser));
    }

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
