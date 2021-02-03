package com.finartz.tasksapp.model.converter;

import com.finartz.tasksapp.model.dto.RoleDto;
import com.finartz.tasksapp.model.request.AddRoleRequest;

public class AddRoleRequestConverter {

    private AddRoleRequestConverter() {
    }

    public static RoleDto convert(AddRoleRequest addRoleRequest) {
        RoleDto role = new RoleDto();

        role.setType(addRoleRequest.getType());

        return role;
    }
}