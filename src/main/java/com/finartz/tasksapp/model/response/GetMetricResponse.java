package com.finartz.tasksapp.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class GetMetricResponse {

    private Long userId;
    private String sprintLabel;
    private Integer commit;
    private Integer bugCount;
    private Integer complete;
}