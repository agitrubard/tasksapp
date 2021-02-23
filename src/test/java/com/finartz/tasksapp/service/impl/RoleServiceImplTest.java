package com.finartz.tasksapp.service.impl;

import com.finartz.tasksapp.model.entity.RoleEntity;
import com.finartz.tasksapp.model.entity.UserEntity;
import com.finartz.tasksapp.model.exception.UserNotFoundException;
import com.finartz.tasksapp.model.request.AddRoleRequest;
import com.finartz.tasksapp.model.response.GetUserRoleResponse;
import com.finartz.tasksapp.model.response.GetUsersRoleResponse;
import com.finartz.tasksapp.repository.RoleRepository;
import com.finartz.tasksapp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.finartz.tasksapp.model.enums.RoleType.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void givenAddRoleRequest_whenCallAddRole_thenAddUserRole() throws UserNotFoundException {
        // Given
        AddRoleRequest addRoleRequest = new AddRoleRequest();
        addRoleRequest.setRoleType(TEAM_LEAD);
        UserEntity mockUserEntity = mock(UserEntity.class);

        // When
        when(mockUserEntity.getId()).thenReturn(1L);
        when(userRepository.findById(mockUserEntity.getId())).thenReturn(Optional.of(mockUserEntity));

        GetUserRoleResponse userRoleResponse = roleService.addRole(mockUserEntity.getId(), addRoleRequest);

        // Then
        assertEquals(userRoleResponse.getRoleType(), addRoleRequest.getRoleType());
    }

    @Test
    void givenAddRoleRequest_whenCallUpdateRole_thenUpdateUserRole() throws UserNotFoundException {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L).build();
        RoleEntity role = RoleEntity.builder()
                .user(user)
                .id(1L)
                .type(DEVELOPER).build();
        AddRoleRequest addRoleRequest = new AddRoleRequest();
        addRoleRequest.setRoleType(TEAM_LEAD);

        // When
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));

        GetUserRoleResponse userRoleResponse = roleService.addRole(role.getUser().getId(), addRoleRequest);

        // Then
        assertEquals(userRoleResponse.getRoleType(), addRoleRequest.getRoleType());
    }

    @Test
    void whenCallAddRole_thenUserNotFoundException() {
        // Given

        // When

        // Then
        assertThrows(UserNotFoundException.class,
                () -> roleService.addRole(1L, new AddRoleRequest()));
    }

    @Test
    void givenRoleEntity_whenCallGetUserRoleByUserId_thenGetUserRole() throws UserNotFoundException {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L).build();
        RoleEntity role = RoleEntity.builder()
                .user(user)
                .id(1L)
                .type(DEVELOPER).build();

        // When
        when(roleRepository.findByUserId(role.getUser().getId())).thenReturn(Optional.of(role));

        GetUserRoleResponse userRoleResponse = roleService.getUserRoleByUserId(role.getUser().getId());

        // Then
        assertEquals(userRoleResponse.getRoleType(), role.getType());
    }

    @Test
    void whenCallGetUserRoleByUserId_thenUserNotFoundException() {
        // Given

        // When

        // Then
        assertThrows(UserNotFoundException.class,
                () -> roleService.getUserRoleByUserId(1L));
    }

    @Test
    void givenRoleEntity_whenCallGetAllUsersRole_thenGetUsersRole() {
        // Given
        UserEntity user = UserEntity.builder()
                .id(1L).build();
        RoleEntity role = RoleEntity.builder()
                .user(user)
                .id(1L)
                .type(DEVELOPER).build();

        // When
        when(roleRepository.findAll()).thenReturn(Collections.singletonList(role));

        List<GetUsersRoleResponse> usersRoleResponse = roleService.getAllUsersRole();

        // Then
        assertEquals(usersRoleResponse.get(0).getUserId(), role.getUser().getId());
    }
}