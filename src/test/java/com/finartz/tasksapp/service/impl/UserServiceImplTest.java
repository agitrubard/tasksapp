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
    void testCreateUser() throws UserAlreadyExistsException {
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
    void testCreateUserException() {
        // Given
        SignupRequest signupRequest = SignupRequest.builder()
                .name("Test-Name")
                .surname("Test-Surname")
                .email("test@email.com")
                .password("123-Test").build();
        UserEntity userMock = mock(UserEntity.class);

        // When
        when(userMock.getEmail()).thenReturn("test@email.com");
        when(userRepository.findByEmail(userMock.getEmail())).thenReturn(Optional.of(userMock));

        // Then
        assertThrows(UserAlreadyExistsException.class, () -> {
            GetUserResponse userResponse = userService.createUser(signupRequest);
            assertEquals(userMock.getEmail(), userResponse.getEmail());
        });
    }

    @Test
    void testLogin() throws UserNotFoundException, PasswordNotCorrectException {
        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@email.com")
                .password("123-Test").build();
        UserEntity userMock = mock(UserEntity.class);

        // When
        when(userMock.getEmail()).thenReturn("test@email.com");
        when(userMock.getPassword()).thenReturn(encoder.encode("123-Test"));
        when(userRepository.findByEmail(userMock.getEmail())).thenReturn(Optional.of(userMock));
        boolean passwordControl = encoder.matches(loginRequest.getPassword(), userMock.getPassword());
        GetUserResponse userResponse = userService.login(loginRequest);

        // Then
        assertEquals(userResponse.getEmail(), loginRequest.getEmail());
        assertTrue(passwordControl);
    }

    @Test
    void testLoginUserException() {
        // Given
        LoginRequest loginRequestMock = mock(LoginRequest.class);

        // When
        when(loginRequestMock.getEmail()).thenReturn("test@email.com");

        // Then
        assertThrows(UserNotFoundException.class, () -> {
            userService.login(loginRequestMock);
        });
    }

    @Test
    void testLoginPasswordException() {
        // Given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("test@email.com")
                .password("123-Test").build();
        UserEntity userMock = mock(UserEntity.class);

        // When
        when(userMock.getEmail()).thenReturn("test@email.com");
        when(userRepository.findByEmail(userMock.getEmail())).thenReturn(Optional.of(userMock));

        // Then
        assertThrows(PasswordNotCorrectException.class, () -> {
            userService.login(loginRequest);
        });
    }


    @Test
    void testUpdateUserByUserId() throws UserNotFoundException {
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
    void testUpdateUserByUserIdUserException() {
        // Given
        // When

        // Then
        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUserByUserId(1L, UpdateUserRequest.builder().build());
        });
    }

    @Test
    void testDeleteUserByUserId() throws UserNotFoundException {
        // Given
        UserEntity userMock = mock(UserEntity.class);

        // When
        when(userMock.getId()).thenReturn(1L);
        when(userRepository.findById(userMock.getId())).thenReturn(Optional.of(userMock));
        userService.deleteUserByUserId(userMock.getId());

        // Then
    }

    @Test
    void testDeleteUserByUserIdUserException() {
        // Given
        UserEntity userMock = mock(UserEntity.class);

        // When
        when(userMock.getId()).thenReturn(1L);

        // Then
        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUserByUserId(userMock.getId());
        });
    }

    @Test
    void testGetUserByUserId() throws UserNotFoundException {
        // Given
        UserEntity userMock = mock(UserEntity.class);

        // When
        when(userMock.getId()).thenReturn(1L);
        when(userMock.getEmail()).thenReturn("test@email.com");
        when(userRepository.findById(userMock.getId())).thenReturn(Optional.of(userMock));
        GetUserResponse userResponse = userService.getUserByUserId(userMock.getId());

        // Then
        assertEquals(userResponse.getEmail(), userMock.getEmail());
    }

    @Test
    void testGetUserByUserIdUserException() {
        // Given
        UserEntity userMock = mock(UserEntity.class);

        // When
        when(userMock.getId()).thenReturn(1L);

        // Then
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserByUserId(userMock.getId());
        });
    }

    @Test
    void testGetAllUsers() {
        // Given
        UserEntity userMock = mock(UserEntity.class);

        // When
        when(userMock.getId()).thenReturn(1L);
        when(userRepository.findAll()).thenReturn(Collections.singletonList(userMock));
        List<GetUsersResponse> usersResponses = userService.getAllUsers();

        // Then
        assertEquals(usersResponses.size(), 1);
    }
}