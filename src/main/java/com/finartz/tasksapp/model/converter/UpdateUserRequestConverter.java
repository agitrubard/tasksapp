package com.finartz.tasksapp.model.converter;

import com.finartz.tasksapp.model.dto.UserDto;
import com.finartz.tasksapp.model.request.UpdateUserRequest;

public class UpdateUserRequestConverter {

    private UpdateUserRequestConverter() {
    }

    public static UserDto convert(UpdateUserRequest updateUserRequest) {
        return UserDto.builder()
                .name(updateUserRequest.getName())
                .surname(updateUserRequest.getSurname())
                .email(updateUserRequest.getEmail()).build();
    }
}