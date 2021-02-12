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
    void testCreateMetricByUserId() throws UserNotFoundException {
        // Given
        CreateMetricRequest createMetricRequest = CreateMetricRequest.builder()
                .sprintLabel("Test-Label")
                .commit(1)
                .bugCount(1)
                .complete(1).build();
        UserEntity userMock = mock(UserEntity.class);

        // When
        when(userMock.getId()).thenReturn(1L);
        when(userRepository.findById(userMock.getId())).thenReturn(Optional.of(userMock));
        GetMetricResponse metricResponse = metricService.createMetricByUserId(userMock.getId(), createMetricRequest);

        // Then
        assertEquals(metricResponse.getSprintLabel(), createMetricRequest.getSprintLabel());
        assertEquals(metricResponse.getCommit(), createMetricRequest.getCommit());
        assertEquals(metricResponse.getComplete(), createMetricRequest.getBugCount());
        assertEquals(metricResponse.getComplete(), createMetricRequest.getComplete());
    }

    @Test
    void testCreateMetricByUserIdUserException() {
        // Given
        CreateMetricRequest createMetricRequest = CreateMetricRequest.builder()
                .sprintLabel("Test-Label")
                .commit(1)
                .bugCount(1)
                .complete(1).build();

        // When

        // Then
        assertThrows(UserNotFoundException.class, () -> {
            metricService.createMetricByUserId(1L, createMetricRequest);
        });
    }

    @Test
    void testUpdateMetricByMetricId() throws MetricNotFoundException {
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
    void testUpdateMetricByMetricIdMetricException() {
        // Given

        // When

        // Then
        assertThrows(MetricNotFoundException.class, () -> {
            metricService.updateMetricByMetricId(1L, UpdateMetricRequest.builder().build());
        });
    }

    @Test
    void testGetMetricByMetricId() throws MetricNotFoundException {
        // Given
        UserEntity userMock = mock(UserEntity.class);
        MetricEntity metricMock = mock(MetricEntity.class);

        // When
        when(userMock.getId()).thenReturn(1L);
        when(metricMock.getId()).thenReturn(1L);
        when(metricMock.getUser()).thenReturn(userMock);
        when(metricMock.getSprintLabel()).thenReturn("Test-Label");
        when(metricRepository.findById(metricMock.getId())).thenReturn(Optional.of(metricMock));
        GetMetricResponse metricResponse = metricService.getMetricByMetricId(metricMock.getId());

        // Then
        assertEquals(metricResponse.getUserId(), userMock.getId());
    }

    @Test
    void testGetMetricByMetricIdMetricException() {
        // Given

        // When

        // Then
        assertThrows(MetricNotFoundException.class, () -> {
            metricService.getMetricByMetricId(1L);
        });
    }

    @Test
    void testGetMetricsByUserId() throws UserNotFoundException, MetricNotFoundException {
        // Given
        UserEntity userMock = mock(UserEntity.class);
        MetricEntity metricMock = mock(MetricEntity.class);

        // When
        when(userMock.getId()).thenReturn(1L);
        when(metricMock.getId()).thenReturn(1L);
        when(metricMock.getUser()).thenReturn(userMock);
        when(userRepository.findById(userMock.getId())).thenReturn(Optional.of(userMock));
        when(metricRepository.findAllByUserId(metricMock.getUser().getId())).thenReturn(Optional.of(Collections.singletonList(metricMock)));
        List<GetUserMetricsResponse> userMetricsResponses = metricService.getMetricsByUserId(userMock.getId());

        // Then
        assertEquals(userMetricsResponses.get(0).getMetricId(), metricMock.getId());
    }

    @Test
    void testGetMetricsByUserIdUserException() {
        // Given

        // When

        // Then
        assertThrows(UserNotFoundException.class, () -> {
            metricService.getMetricsByUserId(1L);
        });
    }

    @Test
    void testGetMetricsByUserIdMetricException() {
        // Given
        UserEntity userMock = mock(UserEntity.class);

        // When
        when(userMock.getId()).thenReturn(1L);
        when(userRepository.findById(userMock.getId())).thenReturn(Optional.of(userMock));

        // Then
        assertThrows(MetricNotFoundException.class, () -> {
            metricService.getMetricsByUserId(userMock.getId());
        });
    }

    @Test
    void testGetAllMetrics() throws MetricNotFoundException {
        // Given
        UserEntity userMock = mock(UserEntity.class);
        MetricEntity metricMock = mock(MetricEntity.class);

        // When
        when(userMock.getId()).thenReturn(1L);
        when(metricMock.getId()).thenReturn(1L);
        when(metricMock.getUser()).thenReturn(userMock);
        when(metricRepository.findAll()).thenReturn(Collections.singletonList(metricMock));
        List<GetMetricsResponse> metricsResponses = metricService.getAllMetrics();

        // Then
        assertEquals(metricsResponses.size(), 1);
    }
}