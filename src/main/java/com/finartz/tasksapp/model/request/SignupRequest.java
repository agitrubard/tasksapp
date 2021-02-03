package com.finartz.tasksapp.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class SignupRequest implements Serializable {

    private static final long serialVersionUID = 5425465019602979867L;
    private String name;
    private String surname;
    private String email;
    private String password;
}