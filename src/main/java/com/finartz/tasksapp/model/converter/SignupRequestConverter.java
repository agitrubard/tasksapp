package com.finartz.tasksapp.model.converter;

import com.finartz.tasksapp.model.dto.UserDto;
import com.finartz.tasksapp.model.request.SignupRequest;

public class SignupRequestConverter {

    private SignupRequestConverter() {
    }

    public static UserDto convert(SignupRequest signupRequest) {
        return UserDto.builder()
                .name(signupRequest.getName())
                .surname(signupRequest.getSurname())
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword()).build();
    }
}