package com.finartz.tasksapp.model.converter;

import com.finartz.tasksapp.model.dto.UserDto;
import com.finartz.tasksapp.model.enums.RoleType;
import com.finartz.tasksapp.model.request.SignupRequest;

public class SignupRequestConverter {

    private SignupRequestConverter() {
    }

    public static UserDto convert(SignupRequest signupRequest) {
        UserDto user = new UserDto();

        user.setName(signupRequest.getName());
        user.setSurname(signupRequest.getSurname());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getPassword());
        user.setRole(RoleType.DEVELOPER);

        return user;
    }
}