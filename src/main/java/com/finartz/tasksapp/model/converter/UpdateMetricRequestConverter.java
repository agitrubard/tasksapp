package com.finartz.tasksapp.model.converter;

import com.finartz.tasksapp.model.dto.MetricDto;
import com.finartz.tasksapp.model.request.UpdateMetricRequest;

public class UpdateMetricRequestConverter {

    private UpdateMetricRequestConverter() {
    }

    public static MetricDto convert(UpdateMetricRequest updateMetricRequest) {
        return MetricDto.builder()
                .sprintLabel(updateMetricRequest.getSprintLabel())
                .commit(updateMetricRequest.getCommit())
                .bugCount(updateMetricRequest.getBugCount())
                .complete(updateMetricRequest.getComplete()).build();
    }
}