package com.goenaga.shop.user.repository;

import com.goenaga.shop.user.enums.Role;
import com.goenaga.shop.user.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void findUserByEmail_ReturnsUser() {
        User user = User.builder()
                .email("test@user")
                .password("test")
                .createdAt(new Date())
                .lastModified(new Date())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        User savedUser = userRepository.findUserByEmail(user.getEmail()).get();

        Assertions.assertThat(savedUser).isNotNull().isInstanceOf(User.class);
        Assertions.assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
    }
}