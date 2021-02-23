package com.finartz.tasksapp.repository;

import com.finartz.tasksapp.model.entity.MetricEntity;
import com.finartz.tasksapp.model.entity.UserEntity;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MetricRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MetricRepository metricRepository;

    private UserEntity user;
    private MetricEntity metric;

    @BeforeEach
    void setUp() {
        user = UserEntity.builder()
                .name("Test-Name")
                .surname("Test-Surname")
                .email("test@email.com")
                .password("123-Test").build();
        entityManager.persist(user);
        metric = MetricEntity.builder()
                .user(user)
                .sprintLabel("Test-Label")
                .commit(0)
                .bugCount(0)
                .complete(0).build();
        entityManager.persist(metric);
        entityManager.flush();
    }

    @Test
    void givenMetricEntity_whenCallFindById_thenReturnMetric() {
        // Given

        // When
        Optional<MetricEntity> foundMetric = metricRepository.findById(1L);

        // Then
        assertThat(user).isEqualTo(foundMetric.get().getUser());
        assertThat(metric.getBugCount()).isEqualTo(foundMetric.get().getBugCount());
    }

    @Test
    void givenMetricEntity_whenCallFindByAllByUserId_thenReturnMetrics() {
        // Given

        // When
        Optional<List<MetricEntity>> foundMetrics = metricRepository.findAllByUserId(2L);

        // Then
        assertThat(user).isEqualTo(foundMetrics.get().get(0).getUser());
        assertThat(metric.getBugCount()).isEqualTo(foundMetrics.get().get(0).getBugCount());
    }
}