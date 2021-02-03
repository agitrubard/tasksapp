package com.finartz.tasksapp.model.converter;

import com.finartz.tasksapp.model.dto.MetricDto;
import com.finartz.tasksapp.model.request.CreateMetricRequest;

public class CreateMetricRequestConverter {

    private CreateMetricRequestConverter() {
    }

    public static MetricDto convert(CreateMetricRequest createMetricRequest) {
        MetricDto metric = new MetricDto();

        metric.setSprintLabel(createMetricRequest.getSprintLabel());
        metric.setCommit(createMetricRequest.getCommit());
        metric.setBugCount(createMetricRequest.getBugCount());
        metric.setComplete(createMetricRequest.getComplete());

        return metric;
    }
}