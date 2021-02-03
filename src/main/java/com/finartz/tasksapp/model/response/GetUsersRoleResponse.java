package com.finartz.tasksapp.model.response;

import com.finartz.tasksapp.model.enums.RoleType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetUsersRoleResponse {

    private Long userId;
    private String name;
    private String surname;
    private RoleType type;
}