package com.goenaga.shop.user.service;

import com.goenaga.shop.auth.model.SignupRequest;
import com.goenaga.shop.user.enums.Role;
import com.goenaga.shop.user.model.User;
import com.goenaga.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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


    public String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
