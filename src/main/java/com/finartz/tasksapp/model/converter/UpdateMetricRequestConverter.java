package com.finartz.tasksapp.model.converter;

import com.finartz.tasksapp.model.dto.MetricDto;
import com.finartz.tasksapp.model.request.UpdateMetricRequest;

public class UpdateMetricRequestConverter {

    private UpdateMetricRequestConverter() {
    }

    public static MetricDto convert(UpdateMetricRequest updateMetricRequest) {
        MetricDto metric = new MetricDto();

        metric.setSprintLabel(updateMetricRequest.getSprintLabel());
        metric.setCommit(updateMetricRequest.getCommit());
        metric.setBugCount(updateMetricRequest.getBugCount());
        metric.setComplete(updateMetricRequest.getComplete());

        return metric;
    }
}