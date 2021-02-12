package com.finartz.tasksapp.controller.endpoint;

import static com.finartz.tasksapp.controller.endpoint.UserControllerEndpoint.USER;
import static com.finartz.tasksapp.controller.endpoint.UserControllerEndpoint.USERS;

public class RoleControllerEndpoint {

    public static final String ROLE_BY_USER_ID = USER + "/{userId}/role";
    public static final String GET_USERS_ROLE = USERS + "/role";
}