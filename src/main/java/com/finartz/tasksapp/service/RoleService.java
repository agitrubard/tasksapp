package com.finartz.tasksapp.service;

import com.finartz.tasksapp.model.exception.UserNotFoundException;
import com.finartz.tasksapp.model.request.AddRoleRequest;
import com.finartz.tasksapp.model.response.GetUserRoleResponse;
import com.finartz.tasksapp.model.response.GetUsersRoleResponse;

import java.util.List;

public interface RoleService {

    void addRole(Long id, AddRoleRequest addRoleRequest) throws UserNotFoundException;

    GetUserRoleResponse getUserRoleByUserId(Long id) throws UserNotFoundException;

    List<GetUsersRoleResponse> getAllUsersRole();
}