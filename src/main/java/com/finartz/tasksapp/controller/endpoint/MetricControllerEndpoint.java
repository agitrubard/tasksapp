package com.finartz.tasksapp.controller.endpoint;

import static com.finartz.tasksapp.controller.endpoint.UserControllerEndpoint.USER;
import static com.finartz.tasksapp.controller.endpoint.UserControllerEndpoint.USERS;

public class MetricControllerEndpoint {

    public static final String CREATE_METRIC_BY_USER_ID = USER + "/{userId}/metric";
    public static final String METRIC_BY_METRIC_ID = USERS + "/metric/{metricId}";
    public static final String GET_METRICS_BY_USER_ID = USER + "/{userId}/metrics";
    public static final String GET_METRICS = USERS + "/metrics";
}