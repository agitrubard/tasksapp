package com.finartz.tasksapp.model.request;

import com.finartz.tasksapp.model.enums.RoleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AddRoleRequest implements Serializable {

    private static final long serialVersionUID = 2331942402825992699L;
    private RoleType roleType;
}