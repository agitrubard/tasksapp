package com.finartz.tasksapp.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
public class UpdateMetricRequest implements Serializable {

    private static final long serialVersionUID = 6809633111241080521L;
    private String sprintLabel;
    private Integer commit;
    private Integer bugCount;
    private Integer complete;
}