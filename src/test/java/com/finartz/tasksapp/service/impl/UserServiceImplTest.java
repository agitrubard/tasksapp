package com.finartz.tasksapp.service.impl;

import com.finartz.tasksapp.model.entity.UserEntity;
import com.finartz.tasksapp.model.exception.PasswordNotCorrectException;
import com.finartz.tasksapp.model.exception.UserAlreadyExistsException;
import com.finartz.tasksapp.model.exception.UserNotFoundException;
import com.finartz.tasksapp.model.request.LoginRequest;
import com.finartz.tasksapp.model.request.SignupRequest;
import com.finartz.tasksapp.model.request.UpdateUserRequest;
import com.finartz.tasksapp.model.response.GetUserResponse;
import com.finartz.tasksapp.model.response.GetUsersResponse;
import com.finartz.tasksapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void givenSignupRequest_whenCallCreateUser_thenCreate() throws UserAlreadyExistsException {
        // Given
        SignupRequest signupRequest = SignupRequest.builder()
                .name("Test-Name")
                .surname("Test-Surname")
                .email("test@email.com")
                .password("123-Test").build();

        // When
        GetUserResponse userResponse = userService.createUser(signupRequest);

        // Then
        assertEquals(userResponse.getEmail(), signupRequest.getEmail());
    }

    @Test
    void givenSignupRequest_whenCallCreate_thenUserAlreadyExistsException() {
        // Given
        SignupRequest signupRequest = SignupRequest.builder()
                .name("Test-Name")
                .surname("Test-Surname")
                .email("test@email.com")
                .password("123-Test").build();
        UserEntity mockUserEntity = mock(UserEntity.class);

        // When
        when(mockUserEntity.getEmail()).thenReturn("test@email.com");
        when(userRepository.findByEmail(mockUserEntity.getEmail())).thenReturn(Optional.of(mockUserEntity));

        // Then
        assertThrows(UserAlreadyExistsException.class, () -> {
            GetUserResponse userResponse = userService.createUser(signupRequest);
            assertEquals(mockUserEntity.getEmail(), userResponse.getEmail());
        });
    }

    @Test
    void givenLoginRequest_whenCallLogin_thenLogin() throws UserNotFoundException, PasswordNotCorrectException {
        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@email.com")
                .password("123-Test").build();
        UserEntity mockUserEntity = mock(UserEntity.class);

        // When
        when(mockUserEntity.getEmail()).thenReturn("test@email.com");
        when(mockUserEntity.getPassword()).thenReturn(encoder.encode("123-Test"));
        when(userRepository.findByEmail(mockUserEntity.getEmail())).thenReturn(Optional.of(mockUserEntity));
        boolean passwordControl = encoder.matches(loginRequest.getPassword(), mockUserEntity.getPassword());
        GetUserResponse userResponse = userService.login(loginRequest);

        // Then
        assertEquals(userResponse.getEmail(), loginRequest.getEmail());
        assertTrue(passwordControl);
    }

    @Test
    void givenLoginRequest_whenCallLogin_thenUserNotFoundException() {
        // Given
        LoginRequest mockLoginRequest = mock(LoginRequest.class);

        // When
        when(mockLoginRequest.getEmail()).thenReturn("test@email.com");

        // Then
        assertThrows(UserNotFoundException.class,
                () -> userService.login(mockLoginRequest));
    }

    @Test
    void givenLoginRequest_whenCallLogin_thenPasswordNotCorrectException() {
        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@email.com")
                .password("123-Test").build();
        UserEntity mockUserEntity = mock(UserEntity.class);

        // When
        when(mockUserEntity.getEmail()).thenReturn("test@email.com");
        when(userRepository.findByEmail(mockUserEntity.getEmail())).thenReturn(Optional.of(mockUserEntity));

        // Then
        assertThrows(PasswordNotCorrectException.class,
                () -> userService.login(loginRequest));
    }


    @Test
    void givenUpdateUserRequest_whenCallUpdateUserByUserId_thenUpdateUser() throws UserNotFoundException {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L)
                .name("Test-Name")
                .surname("Test-Surname")
                .email("test@email.com").build();
        UpdateUserRequest updateUserRequest = UpdateUserRequest.builder()
                .name("Tester-Name")
                .surname("Tester-Surname")
                .email("tester@email.com").build();

        // When
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        GetUserResponse userResponse = userService.updateUserByUserId(user.getId(), updateUserRequest);

        // Then
        assertEquals(userResponse.getName(), updateUserRequest.getName());
        assertEquals(userResponse.getSurname(), updateUserRequest.getSurname());
        assertEquals(userResponse.getEmail(), updateUserRequest.getEmail());
    }

    @Test
    void whenCallUpdateUserByUserId_thenUserNotFoundException() {
        // Given

        // When

        // Then
        assertThrows(UserNotFoundException.class,
                () -> userService.updateUserByUserId(1L, UpdateUserRequest.builder().build()));
    }

    @Test
    void givenUserEntity_whenCallDeleteUserByUserId_thenDeleteUser() throws UserNotFoundException {
        // Given
        UserEntity mockUserEntity = mock(UserEntity.class);

        // When
        when(mockUserEntity.getId()).thenReturn(1L);
        when(userRepository.findById(mockUserEntity.getId())).thenReturn(Optional.of(mockUserEntity));
        userService.deleteUserByUserId(mockUserEntity.getId());

        // Then
    }

    @Test
    void whenCallDeleteUserByUserId_thenUserNotFoundException() {
        // Given

        // When

        // Then
        assertThrows(UserNotFoundException.class,
                () -> userService.deleteUserByUserId(1L));
    }

    @Test
    void givenUserEntity_whenCallGetUserByUserId_thenGetUser() throws UserNotFoundException {
        // Given
        UserEntity mockUserEntity = mock(UserEntity.class);

        // When
        when(mockUserEntity.getId()).thenReturn(1L);
        when(mockUserEntity.getEmail()).thenReturn("test@email.com");
        when(userRepository.findById(mockUserEntity.getId())).thenReturn(Optional.of(mockUserEntity));
        GetUserResponse userResponse = userService.getUserByUserId(mockUserEntity.getId());

        // Then
        assertEquals(userResponse.getEmail(), mockUserEntity.getEmail());
    }

    @Test
    void whenCallGetUserByUserId_thenUserNotFoundException() {
        // Given

        // When

        // Then
        assertThrows(UserNotFoundException.class,
                () -> userService.getUserByUserId(1L));
    }

    @Test
    void givenUserEntity_whenCallGetAllUsers_thenGetUsers() {
        // Given
        UserEntity mockUserEntity = mock(UserEntity.class);

        // When
        when(mockUserEntity.getId()).thenReturn(1L);
        when(userRepository.findAll()).thenReturn(Collections.singletonList(mockUserEntity));
        List<GetUsersResponse> usersResponses = userService.getAllUsers();

        // Then
        assertEquals(usersResponses.get(0).getId(), mockUserEntity.getId());
    }
}