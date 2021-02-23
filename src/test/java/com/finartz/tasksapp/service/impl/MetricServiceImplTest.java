package com.finartz.tasksapp.service.impl;

import com.finartz.tasksapp.model.entity.MetricEntity;
import com.finartz.tasksapp.model.entity.UserEntity;
import com.finartz.tasksapp.model.exception.MetricNotFoundException;
import com.finartz.tasksapp.model.exception.UserNotFoundException;
import com.finartz.tasksapp.model.request.CreateMetricRequest;
import com.finartz.tasksapp.model.request.UpdateMetricRequest;
import com.finartz.tasksapp.model.response.*;
import com.finartz.tasksapp.repository.MetricRepository;
import com.finartz.tasksapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class MetricServiceImplTest {

    @InjectMocks
    private MetricServiceImpl metricService;

    @Mock
    private MetricRepository metricRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void givenCreateMetricRequest_whenCallCreateMetricByUserId_thenCreateMetric() throws UserNotFoundException {
        // Given
        CreateMetricRequest createMetricRequest = CreateMetricRequest.builder()
                .sprintLabel("Test-Label")
                .commit(1)
                .bugCount(1)
                .complete(1).build();
        UserEntity mockUserEntity = mock(UserEntity.class);

        // When
        when(mockUserEntity.getId()).thenReturn(1L);
        when(userRepository.findById(mockUserEntity.getId())).thenReturn(Optional.of(mockUserEntity));
        GetMetricResponse metricResponse = metricService.createMetricByUserId(mockUserEntity.getId(), createMetricRequest);

        // Then
        assertEquals(metricResponse.getSprintLabel(), createMetricRequest.getSprintLabel());
        assertEquals(metricResponse.getCommit(), createMetricRequest.getCommit());
        assertEquals(metricResponse.getComplete(), createMetricRequest.getBugCount());
        assertEquals(metricResponse.getComplete(), createMetricRequest.getComplete());
    }

    @Test
    void whenCallCreateMetricByUserId_thenUserNotFoundException() {
        // Given

        // When

        // Then
        assertThrows(UserNotFoundException.class,
                () -> metricService.createMetricByUserId(1L, CreateMetricRequest.builder().build()));
    }

    @Test
    void givenUpdateMetricRequest_whenCallUpdateMetricByMetricId_thenUpdateMetric() throws MetricNotFoundException {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L).build();
        MetricEntity metric = MetricEntity.builder()
                .user(user)
                .id(1L)
                .sprintLabel("Test-Label")
                .commit(0)
                .bugCount(0)
                .complete(0).build();
        UpdateMetricRequest updateMetricRequest = UpdateMetricRequest.builder()
                .sprintLabel("Test1-Label")
                .commit(1)
                .bugCount(1)
                .complete(1).build();

        // When
        when(metricRepository.findById(metric.getId())).thenReturn(Optional.of(metric));
        GetMetricResponse metricResponse = metricService.updateMetricByMetricId(metric.getId(), updateMetricRequest);

        // Then
        assertEquals(metricResponse.getSprintLabel(), updateMetricRequest.getSprintLabel());
        assertEquals(metricResponse.getCommit(), updateMetricRequest.getCommit());
        assertEquals(metricResponse.getComplete(), updateMetricRequest.getBugCount());
        assertEquals(metricResponse.getComplete(), updateMetricRequest.getComplete());
    }

    @Test
    void whenCallUpdateMetricByMetricId_thenMetricNotFoundException() {
        // Given

        // When

        // Then
        assertThrows(MetricNotFoundException.class,
                () -> metricService.updateMetricByMetricId(1L, UpdateMetricRequest.builder().build()));
    }

    @Test
    void givenMetricEntity_whenCallGetMetricByMetricId_thenGetMetric() throws MetricNotFoundException {
        // Given
        UserEntity mockUserEntity = mock(UserEntity.class);
        MetricEntity mockMetricEntity = mock(MetricEntity.class);

        // When
        when(mockUserEntity.getId()).thenReturn(1L);
        when(mockMetricEntity.getId()).thenReturn(1L);
        when(mockMetricEntity.getUser()).thenReturn(mockUserEntity);
        when(mockMetricEntity.getSprintLabel()).thenReturn("Test-Label");
        when(metricRepository.findById(mockMetricEntity.getId())).thenReturn(Optional.of(mockMetricEntity));
        GetMetricResponse metricResponse = metricService.getMetricByMetricId(mockMetricEntity.getId());

        // Then
        assertEquals(metricResponse.getUserId(), mockUserEntity.getId());
        assertEquals(metricResponse.getSprintLabel(), mockMetricEntity.getSprintLabel());
    }

    @Test
    void whenCallGetMetricByMetricId_thenMetricNotFoundException() {
        // Given

        // When

        // Then
        assertThrows(MetricNotFoundException.class,
                () -> metricService.getMetricByMetricId(1L));
    }

    @Test
    void givenMetricEntity_whenCallGetMetricsByUserId_thenGetMetrics() throws UserNotFoundException, MetricNotFoundException {
        // Given
        UserEntity mockUserEntity = mock(UserEntity.class);
        MetricEntity mockMetricEntity = mock(MetricEntity.class);

        // When
        when(mockUserEntity.getId()).thenReturn(1L);
        when(mockMetricEntity.getId()).thenReturn(1L);
        when(mockMetricEntity.getUser()).thenReturn(mockUserEntity);
        when(userRepository.findById(mockUserEntity.getId())).thenReturn(Optional.of(mockUserEntity));
        when(metricRepository.findAllByUserId(mockMetricEntity.getUser().getId())).thenReturn(Optional.of(Collections.singletonList(mockMetricEntity)));
        List<GetUserMetricsResponse> userMetricsResponses = metricService.getMetricsByUserId(mockUserEntity.getId());

        // Then
        assertEquals(userMetricsResponses.get(0).getMetricId(), mockMetricEntity.getId());
    }

    @Test
    void whenCallGetMetricsByUserId_thenUserNotFoundException() {
        // Given

        // When

        // Then
        assertThrows(UserNotFoundException.class,
                () -> metricService.getMetricsByUserId(1L));
    }

    @Test
    void givenUserEntity_whenCallGetMetricsByUserId_thenMetricNotFoundException() {
        // Given
        UserEntity mockUserEntity = mock(UserEntity.class);

        // When
        when(mockUserEntity.getId()).thenReturn(1L);
        when(userRepository.findById(mockUserEntity.getId())).thenReturn(Optional.of(mockUserEntity));

        // Then
        assertThrows(MetricNotFoundException.class,
                () -> metricService.getMetricsByUserId(mockUserEntity.getId()));
    }

    @Test
    void givenMetricEntity_whenCallGetAllMetrics_thenGetMetrics() throws MetricNotFoundException {
        // Given
        UserEntity mockUserEntity = mock(UserEntity.class);
        MetricEntity mockMetricEntity = mock(MetricEntity.class);

        // When
        when(mockUserEntity.getId()).thenReturn(1L);
        when(mockMetricEntity.getId()).thenReturn(1L);
        when(mockMetricEntity.getUser()).thenReturn(mockUserEntity);
        when(metricRepository.findAll()).thenReturn(Collections.singletonList(mockMetricEntity));
        List<GetMetricsResponse> metricsResponses = metricService.getAllMetrics();

        // Then
        assertEquals(metricsResponses.get(0).getUserId(), mockUserEntity.getId());
    }
}