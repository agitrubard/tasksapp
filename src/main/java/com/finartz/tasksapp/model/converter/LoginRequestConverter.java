package com.finartz.tasksapp.model.converter;

import com.finartz.tasksapp.model.dto.UserDto;
import com.finartz.tasksapp.model.request.LoginRequest;

public class LoginRequestConverter {

    private LoginRequestConverter() {
    }

    public static UserDto convert(LoginRequest loginRequest) {
        UserDto user = new UserDto();

        user.setEmail(loginRequest.getEmail());
        user.setPassword(loginRequest.getPassword());

        return user;
    }
}