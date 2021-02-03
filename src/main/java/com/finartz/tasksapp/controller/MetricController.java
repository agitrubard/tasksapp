package com.finartz.tasksapp.controller;

import com.finartz.tasksapp.controller.endpoint.MetricControllerEndpoint;
import com.finartz.tasksapp.model.exception.MetricNotFoundException;
import com.finartz.tasksapp.model.exception.UserNotFoundException;
import com.finartz.tasksapp.model.request.CreateMetricRequest;
import com.finartz.tasksapp.model.request.UpdateMetricRequest;
import com.finartz.tasksapp.model.response.GetMetricResponse;
import com.finartz.tasksapp.model.response.GetMetricsResponse;
import com.finartz.tasksapp.model.response.GetUserMetricsResponse;
import com.finartz.tasksapp.service.MetricService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class MetricController {

    private final MetricService metricService;

    @PostMapping(value = MetricControllerEndpoint.CREATE_METRIC_BY_USER_ID)
    public void createMetricByUserId(@PathVariable Long userId,
                                     @RequestBody CreateMetricRequest createMetricRequest) throws UserNotFoundException {
        metricService.createMetricByUserId(userId, createMetricRequest);
    }

    @PutMapping(value = MetricControllerEndpoint.METRIC_BY_USER_ID_AND_METRIC_ID)
    public void updateMetricByUserIdAndMetricId(@PathVariable Long userId,
                                                @RequestBody UpdateMetricRequest updateMetricRequest,
                                                @PathVariable Long metricId) throws UserNotFoundException, MetricNotFoundException {
        metricService.updateMetricByUserIdAndMetricId(userId, updateMetricRequest, metricId);
    }

    @GetMapping(value = MetricControllerEndpoint.METRIC_BY_USER_ID_AND_METRIC_ID)
    public GetMetricResponse getMetricByUserIdAndMetricId(@PathVariable Long userId,
                                                          @PathVariable Long metricId) throws UserNotFoundException, MetricNotFoundException {
        return metricService.getMetricByUserIdAndMetricId(userId, metricId);
    }

    @GetMapping(value = MetricControllerEndpoint.GET_METRICS_BY_USER_ID)
    public List<GetUserMetricsResponse> getMetricsByUserId(@PathVariable Long userId) throws UserNotFoundException, MetricNotFoundException {
        return metricService.getMetricsByUserId(userId);
    }

    @GetMapping(value = MetricControllerEndpoint.GET_METRICS)
    public List<GetMetricsResponse> getAllMetrics() throws MetricNotFoundException {
        return metricService.getAllMetrics();
    }
}