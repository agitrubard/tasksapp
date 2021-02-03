package com.finartz.tasksapp.service;

import com.finartz.tasksapp.model.exception.MetricNotFoundException;
import com.finartz.tasksapp.model.exception.UserNotFoundException;
import com.finartz.tasksapp.model.request.CreateMetricRequest;
import com.finartz.tasksapp.model.request.UpdateMetricRequest;
import com.finartz.tasksapp.model.response.GetMetricResponse;
import com.finartz.tasksapp.model.response.GetMetricsResponse;
import com.finartz.tasksapp.model.response.GetUserMetricsResponse;

import java.util.List;

public interface wMetricService {

    void createMetricByUserId(Long userId, CreateMetricRequest createMetricRequest) throws UserNotFoundException;

    void updateMetricByUserIdAndMetricId(Long userId, UpdateMetricRequest updateMetricRequest, Long metricId) throws UserNotFoundException, MetricNotFoundException;

    GetMetricResponse getMetricByUserIdAndMetricId(Long userId, Long metricId) throws UserNotFoundException, MetricNotFoundException;

    List<GetUserMetricsResponse> getMetricsByUserId(Long userId) throws UserNotFoundException, MetricNotFoundException;

    List<GetMetricsResponse> getAllMetrics() throws MetricNotFoundException;
}