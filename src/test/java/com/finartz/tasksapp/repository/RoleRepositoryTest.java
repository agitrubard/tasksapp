package com.finartz.tasksapp.repository;

import com.finartz.tasksapp.model.entity.RoleEntity;
import com.finartz.tasksapp.model.entity.UserEntity;
import com.finartz.tasksapp.model.enums.RoleType;
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
class RoleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void givenRoleEntity_whenCallFindByUserId_thenReturnRole() {
        // Given
        UserEntity user = UserEntity.builder()
                .name("Test-Name")
                .surname("Test-Surname")
                .email("test@email.com")
                .password("123-Test").build();
        entityManager.persist(user);
        RoleEntity role = RoleEntity.builder()
                .user(user)
                .type(RoleType.DEVELOPER).build();
        entityManager.persist(role);
        entityManager.flush();

        // When
        Optional<RoleEntity> foundRole = roleRepository.findByUserId(2L);

        // Then
        assertThat(user).isEqualTo(foundRole.get().getUser());
        assertThat(role.getType()).isEqualTo(foundRole.get().getType());
    }
}