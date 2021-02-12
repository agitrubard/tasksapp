package com.finartz.tasksapp.model.dto;

import com.finartz.tasksapp.model.enums.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
public class RoleDto implements Serializable {

    private static final long serialVersionUID = 9044841782172415696L;
    private RoleType type;
}