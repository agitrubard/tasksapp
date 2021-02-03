package com.finartz.tasksapp.model.dto;

import com.finartz.tasksapp.model.enums.RoleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class RoleDto implements Serializable {

    private static final long serialVersionUID = -576987906715524428L;
    private RoleType type;
}