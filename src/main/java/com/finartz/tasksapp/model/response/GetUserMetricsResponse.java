package com.finartz.tasksapp.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetUserMetricsResponse {

    private Long metricId;
    private String sprintLabel;
    private Integer commit;
    private Integer bugCount;
    private Integer complete;
}