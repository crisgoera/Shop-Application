package com.goenaga.shop.user.repository;

import com.goenaga.shop.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findUserByEmail(String email);
}
