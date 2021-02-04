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
import com.finartz.tasksapp.model.response.constant.ErrorLogConstant;
import com.finartz.tasksapp.repository.MetricRepository;
import com.finartz.tasksapp.repository.UserRepository;
import com.finartz.tasksapp.service.MetricService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MetricServiceImpl implements MetricService {

    private final MetricRepository metricRepository;
    private final UserRepository userRepository;

    @Override
    public void createMetricByUserId(Long userId, CreateMetricRequest createMetricRequest) throws UserNotFoundException {
        log.info("Create Metric By User ID Call Starting");

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (UserServiceImpl.isUserPresent(userEntityOptional)) {
            createMetric(createMetricRequest, userEntityOptional);
        } else {
            log.error(ErrorLogConstant.USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public void updateMetricByUserIdAndMetricId(Long userId, UpdateMetricRequest updateMetricRequest, Long metricId) throws UserNotFoundException, MetricNotFoundException {
        log.info("Update Metric By User ID and Metric ID Call Starting");

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (UserServiceImpl.isUserPresent(userEntityOptional)) {
            Optional<MetricEntity> metricEntityOptional = metricRepository.findByUserIdAndId(userEntityOptional.get().getId(), metricId);

            if (isMetricPresent(metricEntityOptional)) {
                updateMetric(updateMetricRequest, metricId);
            } else {
                log.error(ErrorLogConstant.METRIC_NOT_FOUND);
                throw new MetricNotFoundException();
            }
        } else {
            log.error(ErrorLogConstant.USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public GetMetricResponse getMetricByUserIdAndMetricId(Long userId, Long metricId) throws UserNotFoundException, MetricNotFoundException {
        log.info("Get Metric By User ID and Metric ID Call Starting");

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (UserServiceImpl.isUserPresent(userEntityOptional)) {
            Optional<MetricEntity> metricEntityOptional = metricRepository.findByUserIdAndId(userEntityOptional.get().getId(), metricId);

            if (isMetricPresent(metricEntityOptional)) {
                return getMetric(metricEntityOptional.get());
            } else {
                log.error(ErrorLogConstant.METRIC_NOT_FOUND);
                throw new MetricNotFoundException();
            }
        } else {
            log.error(ErrorLogConstant.USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<GetUserMetricsResponse> getMetricsByUserId(Long userId) throws UserNotFoundException, MetricNotFoundException {
        log.info("Get Metrics By User ID Call Starting");

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (UserServiceImpl.isUserPresent(userEntityOptional)) {
            Optional<List<MetricEntity>> metricEntitiesOptional = metricRepository.findAllByUserId(userEntityOptional.get().getId());

            if (areMetricsPresent(metricEntitiesOptional)) {
                return getUserMetricsResponses(metricEntitiesOptional.get());
            } else {
                log.error(ErrorLogConstant.METRIC_NOT_FOUND);
                throw new MetricNotFoundException();
            }
        } else {
            log.error(ErrorLogConstant.USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<GetMetricsResponse> getAllMetrics() throws MetricNotFoundException {
        log.info("Get All Metrics Call Starting");

        List<MetricEntity> metricEntities = metricRepository.findAll();

        boolean metricIsEmpty = metricEntities.isEmpty();
        if (!metricIsEmpty) {
            return getAllMetricsResponses(metricEntities);
        } else {
            log.error(ErrorLogConstant.METRIC_NOT_FOUND);
            throw new MetricNotFoundException();
        }
    }

    private void createMetric(CreateMetricRequest createMetricRequest, Optional<UserEntity> userEntityOptional) throws UserNotFoundException {
        log.info("Create Metric Call Starting");

        MetricDto metric = CreateMetricRequestConverter.convert(createMetricRequest);
        MetricEntity metricEntity = new MetricEntity();

        if (UserServiceImpl.isUserPresent(userEntityOptional)) {
            metricEntity.setUser(userEntityOptional.get());
            metricEntity.setSprintLabel(metric.getSprintLabel());
            metricEntity.setCommit(metric.getCommit());
            metricEntity.setBugCount(metric.getBugCount());
            metricEntity.setComplete(metric.getComplete());

            metricRepository.save(metricEntity);
        } else {
            log.error(ErrorLogConstant.USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    private void updateMetric(UpdateMetricRequest updateMetricRequest, Long metricId) throws MetricNotFoundException {
        log.info("Update Metric Call Starting");

        Optional<MetricEntity> metricEntityOptional = metricRepository.findById(metricId);

        if (isMetricPresent(metricEntityOptional)) {
            MetricDto metric = UpdateMetricRequestConverter.convert(updateMetricRequest);

            metricEntityOptional.get().setSprintLabel(metric.getSprintLabel());
            metricEntityOptional.get().setCommit(metric.getCommit());
            metricEntityOptional.get().setBugCount(metric.getBugCount());
            metricEntityOptional.get().setComplete(metric.getComplete());

            metricRepository.save(metricEntityOptional.get());
        } else {
            log.error(ErrorLogConstant.METRIC_NOT_FOUND);
            throw new MetricNotFoundException();
        }
    }

    private GetMetricResponse getMetric(MetricEntity metricEntity) {
        log.info("Get Metric Call Starting");

        GetMetricResponse getMetricResponse = new GetMetricResponse();

        getMetricResponse.setSprintLabel(metricEntity.getSprintLabel());
        getMetricResponse.setCommit(metricEntity.getCommit());
        getMetricResponse.setBugCount(metricEntity.getBugCount());
        getMetricResponse.setComplete(metricEntity.getComplete());

        return getMetricResponse;
    }

    private GetUserMetricsResponse getUserMetrics(MetricEntity metricEntity) {
        log.info("Get User Metrics Call Starting");

        GetUserMetricsResponse getUserMetricsResponse = new GetUserMetricsResponse();

        getUserMetricsResponse.setMetricId(metricEntity.getId());
        getUserMetricsResponse.setSprintLabel(metricEntity.getSprintLabel());
        getUserMetricsResponse.setCommit(metricEntity.getCommit());
        getUserMetricsResponse.setBugCount(metricEntity.getBugCount());
        getUserMetricsResponse.setComplete(metricEntity.getComplete());

        return getUserMetricsResponse;
    }

    private GetMetricsResponse getMetrics(MetricEntity metricEntity) {
        log.info("Get Metrics Call Starting");

        GetMetricsResponse getMetricsResponse = new GetMetricsResponse();

        getMetricsResponse.setUserId(metricEntity.getUser().getId());
        getMetricsResponse.setMetricId(metricEntity.getId());
        getMetricsResponse.setSprintLabel(metricEntity.getSprintLabel());
        getMetricsResponse.setCommit(metricEntity.getCommit());
        getMetricsResponse.setBugCount(metricEntity.getBugCount());
        getMetricsResponse.setComplete(metricEntity.getComplete());

        return getMetricsResponse;
    }

    private List<GetUserMetricsResponse> getUserMetricsResponses(List<MetricEntity> metricEntities) throws MetricNotFoundException {
        log.info("Get User Metrics Responses Call Starting");

        return Optional.of(metricEntities.stream()
                .map(this::getUserMetrics).collect(Collectors.toList()))
                .orElseThrow(MetricNotFoundException::new);
    }

    private List<GetMetricsResponse> getAllMetricsResponses(List<MetricEntity> metricEntities) throws MetricNotFoundException {
        log.info("Get All Metrics Responses Call Starting");

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