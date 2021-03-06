package com.finartz.tasksapp.service.impl;

import com.finartz.tasksapp.model.converter.CreateMetricRequestConverter;
import com.finartz.tasksapp.model.converter.UpdateMetricRequestConverter;
import com.finartz.tasksapp.model.dto.MetricDto;
import com.finartz.tasksapp.model.entity.MetricEntity;
import com.finartz.tasksapp.model.entity.UserEntity;
import com.finartz.tasksapp.model.exception.MetricNotFoundException;
import com.finartz.tasksapp.model.exception.UserNotFoundException;
import com.finartz.tasksapp.model.request.CreateMetricRequest;
import com.finartz.tasksapp.model.request.UpdateMetricRequest;
import com.finartz.tasksapp.model.response.GetMetricResponse;
import com.finartz.tasksapp.model.response.GetMetricsResponse;
import com.finartz.tasksapp.model.response.GetUserMetricsResponse;
import com.finartz.tasksapp.repository.MetricRepository;
import com.finartz.tasksapp.repository.UserRepository;
import com.finartz.tasksapp.service.MetricService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.finartz.tasksapp.service.impl.UserServiceImpl.isUserPresent;
import static com.finartz.tasksapp.model.response.constant.ErrorLogConstant.*;

@Service
@AllArgsConstructor
@Slf4j
public class MetricServiceImpl implements MetricService {

    private final MetricRepository metricRepository;
    private final UserRepository userRepository;

    @Override
    public GetMetricResponse createMetricByUserId(Long userId, CreateMetricRequest createMetricRequest) throws UserNotFoundException {
        log.debug("Create Metric By User ID Call Starting");

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (isUserPresent(userEntityOptional)) {
            return createMetric(createMetricRequest, userEntityOptional);
        } else {
            log.error(USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public GetMetricResponse updateMetricByMetricId(Long metricId, UpdateMetricRequest updateMetricRequest) throws MetricNotFoundException {
        log.debug("Update Metric By User ID and Metric ID Call Starting");

        Optional<MetricEntity> metricEntityOptional = metricRepository.findById(metricId);

        if (isMetricPresent(metricEntityOptional)) {
            return updateMetric(metricEntityOptional.get(), updateMetricRequest);
        } else {
            log.error(METRIC_NOT_FOUND);
            throw new MetricNotFoundException();
        }
    }

    @Override
    public GetMetricResponse getMetricByMetricId(Long metricId) throws MetricNotFoundException {
        log.debug("Get Metric By User ID and Metric ID Call Starting");

        Optional<MetricEntity> metricEntityOptional = metricRepository.findById(metricId);

        if (isMetricPresent(metricEntityOptional)) {
            return getMetric(metricEntityOptional.get());
        } else {
            log.error(METRIC_NOT_FOUND);
            throw new MetricNotFoundException();
        }
    }

    @Override
    public List<GetUserMetricsResponse> getMetricsByUserId(Long userId) throws UserNotFoundException, MetricNotFoundException {
        log.debug("Get Metrics By User ID Call Starting");

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (isUserPresent(userEntityOptional)) {
            Optional<List<MetricEntity>> metricEntitiesOptional = metricRepository.findAllByUserId(userEntityOptional.get().getId());

            if (areMetricsPresent(metricEntitiesOptional)) {
                return getUserMetricsResponses(metricEntitiesOptional.get());
            } else {
                log.error(METRIC_NOT_FOUND);
                throw new MetricNotFoundException();
            }
        } else {
            log.error(USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<GetMetricsResponse> getAllMetrics() throws MetricNotFoundException {
        log.debug("Get All Metrics Call Starting");

        List<MetricEntity> metricEntities = metricRepository.findAll();
        return getAllMetricsResponses(metricEntities);
    }

    private GetMetricResponse createMetric(CreateMetricRequest createMetricRequest, Optional<UserEntity> userEntityOptional) {
        log.debug("Create Metric Call Starting");

        MetricDto metric = CreateMetricRequestConverter.convert(createMetricRequest);

        MetricEntity metricEntity = MetricEntity.builder()
                .user(userEntityOptional.get())
                .sprintLabel(metric.getSprintLabel())
                .commit(metric.getCommit())
                .bugCount(metric.getBugCount())
                .complete(metric.getComplete()).build();

        metricRepository.save(metricEntity);
        return getMetric(metricEntity);
    }

    private GetMetricResponse updateMetric(MetricEntity metricEntity, UpdateMetricRequest updateMetricRequest) {
        log.debug("Update Metric Call Starting");

        MetricDto metric = UpdateMetricRequestConverter.convert(updateMetricRequest);

        metricEntity.setSprintLabel(metric.getSprintLabel());
        metricEntity.setCommit(metric.getCommit());
        metricEntity.setBugCount(metric.getBugCount());
        metricEntity.setComplete(metric.getComplete());

        metricRepository.save(metricEntity);
        return getMetric(metricEntity);
    }

    private GetMetricResponse getMetric(MetricEntity metricEntity) {
        log.debug("Get Metric Call Starting");

        return GetMetricResponse.builder()
                .userId(metricEntity.getUser().getId())
                .sprintLabel(metricEntity.getSprintLabel())
                .commit(metricEntity.getCommit())
                .bugCount(metricEntity.getBugCount())
                .complete(metricEntity.getComplete()).build();
    }

    private GetUserMetricsResponse getUserMetrics(MetricEntity metricEntity) {
        log.debug("Get User Metrics Call Starting");

        return GetUserMetricsResponse.builder()
                .metricId(metricEntity.getId())
                .sprintLabel(metricEntity.getSprintLabel())
                .commit(metricEntity.getCommit())
                .bugCount(metricEntity.getBugCount())
                .complete(metricEntity.getComplete()).build();
    }

    private GetMetricsResponse getMetrics(MetricEntity metricEntity) {
        log.debug("Get Metrics Call Starting");

        return GetMetricsResponse.builder()
                .userId(metricEntity.getUser().getId())
                .metricId(metricEntity.getId())
                .sprintLabel(metricEntity.getSprintLabel())
                .commit(metricEntity.getCommit())
                .bugCount(metricEntity.getBugCount())
                .complete(metricEntity.getComplete()).build();
    }

    private List<GetUserMetricsResponse> getUserMetricsResponses(List<MetricEntity> metricEntities) throws MetricNotFoundException {
        log.debug("Get User Metrics Responses Call Starting");

        return Optional.of(metricEntities.stream()
                .map(this::getUserMetrics).collect(Collectors.toList()))
                .orElseThrow(MetricNotFoundException::new);
    }

    private List<GetMetricsResponse> getAllMetricsResponses(List<MetricEntity> metricEntities) throws MetricNotFoundException {
        log.debug("Get All Metrics Responses Call Starting");

        return Optional.of(metricEntities.stream()
                .map(this::getMetrics).collect(Collectors.toList()))
                .orElseThrow(MetricNotFoundException::new);
    }

    private static boolean isMetricPresent(Optional<MetricEntity> metricEntityOptional) {
        return metricEntityOptional.isPresent();
    }

    private static boolean areMetricsPresent(Optional<List<MetricEntity>> metricEntitiesOptional) {
        return metricEntitiesOptional.isPresent();
    }
}