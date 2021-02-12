package com.finartz.tasksapp.controller;

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

import static com.finartz.tasksapp.controller.endpoint.MetricControllerEndpoint.*;

@RestController
@AllArgsConstructor
public class MetricController {

    private final MetricService metricService;

    @PostMapping(value = CREATE_METRIC_BY_USER_ID)
    public void createMetricByUserId(@PathVariable Long userId,
                                     @RequestBody CreateMetricRequest createMetricRequest) throws UserNotFoundException {
        metricService.createMetricByUserId(userId, createMetricRequest);
    }

    @PutMapping(value = METRIC_BY_METRIC_ID)
    public void updateMetricByMetricId(@PathVariable Long metricId,
                                       @RequestBody UpdateMetricRequest updateMetricRequest) throws MetricNotFoundException {
        metricService.updateMetricByMetricId(metricId, updateMetricRequest);
    }

    @GetMapping(value = METRIC_BY_METRIC_ID)
    public GetMetricResponse getMetricByMetricId(@PathVariable Long metricId) throws MetricNotFoundException {
        return metricService.getMetricByMetricId(metricId);
    }

    @GetMapping(value = GET_METRICS_BY_USER_ID)
    public List<GetUserMetricsResponse> getMetricsByUserId(@PathVariable Long userId) throws UserNotFoundException, MetricNotFoundException {
        return metricService.getMetricsByUserId(userId);
    }

    @GetMapping(value = GET_METRICS)
    public List<GetMetricsResponse> getAllMetrics() throws MetricNotFoundException {
        return metricService.getAllMetrics();
    }
}