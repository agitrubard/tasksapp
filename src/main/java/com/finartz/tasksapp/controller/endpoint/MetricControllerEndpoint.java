package com.finartz.tasksapp.controller.endpoint;

public class MetricControllerEndpoint {

    public static final String CREATE_METRIC_BY_USER_ID = UserControllerEndpoint.USER + "/{userId}/metric";
    public static final String METRIC_BY_USER_ID_AND_METRIC_ID = UserControllerEndpoint.USER + "/{userId}/metric/{metricId}";
    public static final String GET_METRICS_BY_USER_ID = UserControllerEndpoint.USER + "/{userId}/metrics";
    public static final String GET_METRICS = UserControllerEndpoint.USERS + "/metrics";
}