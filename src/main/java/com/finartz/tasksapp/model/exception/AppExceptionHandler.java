package com.finartz.tasksapp.model.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({UserAlreadyExistsException.class})
    public String userAlreadyExists() {
        return "User is already exists!";
    }

    @ExceptionHandler({UserNotFoundException.class})
    public String userNotFoundException() {
        return "User is not found!";
    }

    @ExceptionHandler({MetricNotFoundException.class})
    public String metricNotFound() {
        return "Metric is not found!";
    }

    @ExceptionHandler({MetricAlreadyExistException.class})
    public String metricAlreadyExist() {
        return "User's metric is already exist!";
    }

    @ExceptionHandler({PasswordNotCorrectException.class})
    public String passwordNotCorrect() {
        return "Password is not correct!";
    }
}