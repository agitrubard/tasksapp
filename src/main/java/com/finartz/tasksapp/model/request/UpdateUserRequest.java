package com.finartz.tasksapp.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
public class UpdateUserRequest implements Serializable {

    private static final long serialVersionUID = 7989289751225201401L;
    private String name;
    private String surname;
    private String email;
}