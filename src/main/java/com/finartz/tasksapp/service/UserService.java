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

    GetUserResponse createUser(SignupRequest signupRequest) throws UserAlreadyExistsException;

    GetUserResponse login(LoginRequest loginRequest) throws UserNotFoundException, PasswordNotCorrectException;

    GetUserResponse updateUserByUserId(Long userId, UpdateUserRequest updateUserRequest) throws UserNotFoundException;

    GetUserResponse deleteUserByUserId(Long userId) throws UserNotFoundException;

    GetUserResponse getUserByUserId(@PathVariable Long userId) throws UserNotFoundException;

    List<GetUsersResponse> getAllUsers();
}