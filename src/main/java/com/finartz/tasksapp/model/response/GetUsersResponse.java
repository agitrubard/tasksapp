package com.finartz.tasksapp.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetUsersResponse {

    private Long id;
    private String name;
    private String surname;
    private String email;
}