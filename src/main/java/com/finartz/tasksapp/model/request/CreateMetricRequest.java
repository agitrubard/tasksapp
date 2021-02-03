package com.finartz.tasksapp.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class CreateMetricRequest implements Serializable {

    private static final long serialVersionUID = 7657458533142559467L;
    private String sprintLabel;
    private Integer commit;
    private Integer bugCount;
    private Integer complete;
}