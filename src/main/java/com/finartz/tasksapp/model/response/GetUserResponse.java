package com.finartz.tasksapp.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class GetUserResponse {

    private String name;
    private String surname;
    private String email;
}