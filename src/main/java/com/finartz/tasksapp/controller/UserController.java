package com.finartz.tasksapp.controller;

import com.finartz.tasksapp.controller.endpoint.UserControllerEndpoint;
import com.finartz.tasksapp.model.exception.PasswordNotCorrectException;
import com.finartz.tasksapp.model.exception.UserAlreadyExistsException;
import com.finartz.tasksapp.model.exception.UserNotFoundException;
import com.finartz.tasksapp.model.request.LoginRequest;
import com.finartz.tasksapp.model.request.SignupRequest;
import com.finartz.tasksapp.model.request.UpdateUserRequest;
import com.finartz.tasksapp.model.response.GetUserResponse;
import com.finartz.tasksapp.model.response.GetUsersResponse;
import com.finartz.tasksapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = UserControllerEndpoint.SIGN_UP)
    public void signUp(@RequestBody SignupRequest signupRequest) throws UserAlreadyExistsException {
        userService.createUser(signupRequest);
    }

    @PostMapping(value = UserControllerEndpoint.LOG_IN)
    public void logIn(@RequestBody LoginRequest loginRequest) throws UserNotFoundException, PasswordNotCorrectException {
        userService.login(loginRequest);
    }

    @PutMapping(value = UserControllerEndpoint.USER_ID)
    public void updateUserByUserId(@PathVariable Long userId,
                                   @RequestBody UpdateUserRequest updateUserRequest) throws UserNotFoundException {
        userService.updateUserByUserId(userId, updateUserRequest);
    }

    @DeleteMapping(value = UserControllerEndpoint.USER_ID)
    public void deleteUserByUserId(@PathVariable Long userId) throws UserNotFoundException {
        userService.deleteUserByUserId(userId);
    }

    @GetMapping(value = UserControllerEndpoint.USER_ID)
    public GetUserResponse getUserByUserId(@PathVariable Long userId) throws UserNotFoundException {
        return userService.getUserByUserId(userId);
    }

    @GetMapping(value = UserControllerEndpoint.USERS)
    public List<GetUsersResponse> getAllUsers() {
        return userService.getAllUsers();
    }
}