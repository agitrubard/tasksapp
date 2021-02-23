package com.finartz.tasksapp.repository;

import com.finartz.tasksapp.model.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .name("Test-Name")
                .surname("Test-Surname")
                .email("test@email.com")
                .password("123-Test").build();
        entityManager.persist(user);
        entityManager.flush();
    }

    @Test
    void givenUserEntity_whenCallFindById_thenReturnUser() {
        // Given

        // When
        Optional<UserEntity> foundUser = userRepository.findById(2L);

        // Then
        assertThat(user).isEqualTo(foundUser.get());
    }

    @Test
    void givenUserEntity_whenCallFindByEmail_thenReturnUser() {
        // Given

        // When
        Optional<UserEntity> foundUser = userRepository.findByEmail(user.getEmail());

        // Then
        assertThat(user).isEqualTo(foundUser.get());
    }
}