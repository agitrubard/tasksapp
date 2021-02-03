package com.finartz.tasksapp.controller;

import com.finartz.tasksapp.controller.endpoint.RoleControllerEndpoint;
import com.finartz.tasksapp.model.exception.UserNotFoundException;
import com.finartz.tasksapp.model.request.AddRoleRequest;
import com.finartz.tasksapp.model.response.GetUserRoleResponse;
import com.finartz.tasksapp.model.response.GetUsersRoleResponse;
import com.finartz.tasksapp.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping(value = RoleControllerEndpoint.ROLE_BY_USER_ID)
    public void addRoleByUserId(@PathVariable Long userId,
                                @RequestBody AddRoleRequest addRoleRequest) throws UserNotFoundException {
        roleService.addRole(userId, addRoleRequest);
    }

    @GetMapping(value = RoleControllerEndpoint.ROLE_BY_USER_ID)
    public GetUserRoleResponse getUserRoleByUserId(@PathVariable Long userId) throws UserNotFoundException {
        return roleService.getUserRoleByUserId(userId);
    }

    @GetMapping(value = RoleControllerEndpoint.GET_USERS_ROLE)
    public List<GetUsersRoleResponse> getAllUsersRole() {
        return roleService.getAllUsersRole();
    }
}