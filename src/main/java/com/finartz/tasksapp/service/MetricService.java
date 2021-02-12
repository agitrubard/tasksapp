package com.finartz.tasksapp.service;

import com.finartz.tasksapp.model.exception.MetricNotFoundException;
import com.finartz.tasksapp.model.exception.UserNotFoundException;
import com.finartz.tasksapp.model.request.CreateMetricRequest;
import com.finartz.tasksapp.model.request.UpdateMetricRequest;
import com.finartz.tasksapp.model.response.GetMetricResponse;
import com.finartz.tasksapp.model.response.GetMetricsResponse;
import com.finartz.tasksapp.model.response.GetUserMetricsResponse;

import java.util.List;

public interface MetricService {

    GetMetricResponse createMetricByUserId(Long userId, CreateMetricRequest createMetricRequest) throws UserNotFoundException;

    GetMetricResponse updateMetricByMetricId(Long metricId, UpdateMetricRequest updateMetricRequest) throws MetricNotFoundException;

    GetMetricResponse getMetricByMetricId(Long metricId) throws MetricNotFoundException;

    List<GetUserMetricsResponse> getMetricsByUserId(Long userId) throws UserNotFoundException, MetricNotFoundException;

    List<GetMetricsResponse> getAllMetrics() throws MetricNotFoundException;
}