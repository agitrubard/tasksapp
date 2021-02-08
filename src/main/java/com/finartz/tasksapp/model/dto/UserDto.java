package com.finartz.tasksapp.model.dto;

import com.finartz.tasksapp.model.enums.RoleType;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
public class UserDto implements Serializable {

    private static final long serialVersionUID = -3014577651338013293L;
    private String name;
    private String surname;
    private String email;
    private String password;
    private RoleType role;
}