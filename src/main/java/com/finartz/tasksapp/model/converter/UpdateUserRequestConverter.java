package com.finartz.tasksapp.model.converter;

import com.finartz.tasksapp.model.dto.UserDto;
import com.finartz.tasksapp.model.request.UpdateUserRequest;

public class UpdateUserRequestConverter {

    private UpdateUserRequestConverter() {
    }

    public static UserDto convert(UpdateUserRequest updateUserRequest) {
        UserDto user = new UserDto();

        user.setName(updateUserRequest.getName());
        user.setSurname(updateUserRequest.getSurname());
        user.setEmail(updateUserRequest.getEmail());

        return user;
    }
}