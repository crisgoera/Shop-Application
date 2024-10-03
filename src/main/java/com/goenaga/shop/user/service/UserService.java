package com.goenaga.shop.user.service;

import com.goenaga.shop.auth.model.SignupRequest;
import com.goenaga.shop.user.enums.Role;
import com.goenaga.shop.user.model.User;
import com.goenaga.shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createNewUser(SignupRequest request) {
        Optional<User> user = userRepository.findUserByEmail(request.getEmail());
        if (!user.isEmpty()) throw new UsernameNotFoundException("Already existing user");

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
        return userRepository.save(newUser);
    }


    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
