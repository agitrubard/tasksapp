package com.finartz.tasksapp.model.converter;

import com.finartz.tasksapp.model.dto.MetricDto;
import com.finartz.tasksapp.model.request.CreateMetricRequest;

public class CreateMetricRequestConverter {

    private CreateMetricRequestConverter() {
    }

    public static MetricDto convert(CreateMetricRequest createMetricRequest) {
        return MetricDto.builder()
                .sprintLabel(createMetricRequest.getSprintLabel())
                .commit(createMetricRequest.getCommit())
                .bugCount(createMetricRequest.getBugCount())
                .complete(createMetricRequest.getComplete()).build();
    }
}