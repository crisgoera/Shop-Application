package com.goenaga.shop.user.service;

import com.goenaga.shop.auth.model.SignupRequest;
import com.goenaga.shop.user.model.User;
import com.goenaga.shop.user.model.UserDTO;

import java.util.Optional;

public interface UserService {

    UserDTO getUserFromToken(String token);

    UserDTO updateUserProfile(String token, UserDTO updateDetails);

    Optional<User> findUserByEmail(String email);

    User createNewUser(SignupRequest request);

    void save(User newUser);
}
