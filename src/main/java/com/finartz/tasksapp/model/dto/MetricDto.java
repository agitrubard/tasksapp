package com.finartz.tasksapp.model.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
public class MetricDto implements Serializable {

    private static final long serialVersionUID = 3561357931331781397L;
    private String sprintLabel;
    private Integer commit;
    private Integer bugCount;
    private Integer complete;
}