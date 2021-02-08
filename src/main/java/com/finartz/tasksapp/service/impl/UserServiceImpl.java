package com.finartz.tasksapp.service.impl;

import com.finartz.tasksapp.model.converter.LoginRequestConverter;
import com.finartz.tasksapp.model.converter.SignupRequestConverter;
import com.finartz.tasksapp.model.converter.UpdateUserRequestConverter;
import com.finartz.tasksapp.model.dto.UserDto;
import com.finartz.tasksapp.model.entity.UserEntity;
import com.finartz.tasksapp.model.exception.PasswordNotCorrectException;
import com.finartz.tasksapp.model.exception.UserAlreadyExistsException;
import com.finartz.tasksapp.model.exception.UserNotFoundException;
import com.finartz.tasksapp.model.request.LoginRequest;
import com.finartz.tasksapp.model.request.SignupRequest;
import com.finartz.tasksapp.model.request.UpdateUserRequest;
import com.finartz.tasksapp.model.response.GetUserResponse;
import com.finartz.tasksapp.model.response.GetUsersResponse;
import com.finartz.tasksapp.model.response.constant.ErrorLogConstant;
import com.finartz.tasksapp.repository.UserRepository;
import com.finartz.tasksapp.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void createUser(SignupRequest signupRequest) throws UserAlreadyExistsException {
        log.debug("Create User Call Starting");

        UserDto user = SignupRequestConverter.convert(signupRequest);
        save(user);
    }

    @Override
    public void login(LoginRequest loginRequest) throws UserNotFoundException, PasswordNotCorrectException {
        log.debug("Log In Call Starting");

        UserDto user = LoginRequestConverter.convert(loginRequest);
        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(user.getEmail());

        if (isUserPresent(userEntityOptional)) {
            loginPasswordControl(loginRequest, userEntityOptional);
        } else {
            log.error(ErrorLogConstant.USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public void updateUserByUserId(Long userId, UpdateUserRequest updateUserRequest) throws UserNotFoundException {
        log.debug("Update User By UserId Call Starting");

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (isUserPresent(userEntityOptional)) {
            UserDto user = UpdateUserRequestConverter.convert(updateUserRequest);
            updateUser(user, userEntityOptional.get());
        } else {
            log.error(ErrorLogConstant.USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public void deleteUserByUserId(Long userId) throws UserNotFoundException {
        log.debug("Delete User By UserId Call Starting");

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (isUserPresent(userEntityOptional)) {
            userRepository.delete(userEntityOptional.get());
        } else {
            log.error(ErrorLogConstant.USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public GetUserResponse getUserByUserId(Long userId) throws UserNotFoundException {
        log.debug("Get User By UserId Call Starting");

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (isUserPresent(userEntityOptional)) {
            return getUser(userEntityOptional.get());
        } else {
            log.error(ErrorLogConstant.USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<GetUsersResponse> getAllUsers() {
        log.debug("Get All User Call Starting");

        List<UserEntity> userEntities = userRepository.findAll();
        return getUsersResponses(userEntities);
    }

    private void save(UserDto user) throws UserAlreadyExistsException {
        log.debug("Save Call Starting");

        Optional<UserEntity> userEntityOptional = userRepository.findByEmail(user.getEmail());

        if (!isUserPresent(userEntityOptional)) {
            saveUser(user);
        } else {
            log.error("User Already Exists!");
            throw new UserAlreadyExistsException();
        }
    }

    private void saveUser(UserDto user) {
        log.debug("Save User Call Starting");

        UserEntity userEntity = UserEntity.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(passwordEncoder(user.getPassword())).build();

        userRepository.save(userEntity);
    }

    private void loginPasswordControl(LoginRequest loginRequest, Optional<UserEntity> userEntityOptional) throws PasswordNotCorrectException {
        log.debug("Log In Password Control Call Starting");

        boolean passwordControl = encoder.matches(loginRequest.getPassword(), userEntityOptional.get().getPassword());
        if (!passwordControl) {
            log.error("Password Not Correct!");
            throw new PasswordNotCorrectException();
        }
    }

    private void updateUser(UserDto user, UserEntity userEntity) {
        log.debug("Update User Call Starting");

        userEntity.setName(user.getName());
        userEntity.setSurname(user.getSurname());
        userEntity.setEmail(user.getEmail());

        userRepository.save(userEntity);
    }

    private GetUserResponse getUser(UserEntity userEntity) {
        log.debug("Get User Call Starting");

        return GetUserResponse.builder()
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .email(userEntity.getEmail()).build();
    }

    private GetUsersResponse getUsers(UserEntity userEntity) {
        log.debug("Get Users Call Starting");

        return GetUsersResponse.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .email(userEntity.getEmail()).build();
    }

    private List<GetUsersResponse> getUsersResponses(List<UserEntity> userEntities) {
        log.debug("Get Users Responses Call Starting");

        return userEntities.stream().map(this::getUsers).collect(Collectors.toList());
    }

    public static boolean isUserPresent(Optional<UserEntity> userEntityOptional) {
        return userEntityOptional.isPresent();
    }

    private String passwordEncoder(String password) {
        log.debug("Password Encoder Call Starting");

        return encoder.encode(password);
    }
}