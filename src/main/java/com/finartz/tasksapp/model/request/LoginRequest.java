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
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 7733957934822002957L;
    private String email;
    private String password;
}