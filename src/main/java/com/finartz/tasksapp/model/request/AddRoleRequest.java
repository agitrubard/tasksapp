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

    private static final long serialVersionUID = -2747877397842797594L;
    private RoleType type;
}