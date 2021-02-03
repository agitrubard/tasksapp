package com.finartz.tasksapp.service;

import com.finartz.tasksapp.model.exception.PasswordNotCorrectException;
import com.finartz.tasksapp.model.exception.UserAlreadyExistsException;
import com.finartz.tasksapp.model.exception.UserNotFoundException;
import com.finartz.tasksapp.model.request.LoginRequest;
import com.finartz.tasksapp.model.request.SignupRequest;
import com.finartz.tasksapp.model.request.UpdateUserRequest;
import com.finartz.tasksapp.model.response.GetUserResponse;
import com.finartz.tasksapp.model.response.GetUsersResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserService {

    void createUser(SignupRequest signupRequest) throws UserAlreadyExistsException;

    void login(LoginRequest loginRequest) throws UserNotFoundException, PasswordNotCorrectException;

    void updateUserByUserId(Long userId, UpdateUserRequest updateUserRequest) throws UserNotFoundException;

    void deleteUserByUserId(Long userId) throws UserNotFoundException;

    GetUserResponse getUserByUserId(@PathVariable Long userId) throws UserNotFoundException;

    List<GetUsersResponse> getAllUsers();
}