package com.finartz.tasksapp.service.impl;

import com.finartz.tasksapp.model.converter.AddRoleRequestConverter;
import com.finartz.tasksapp.model.dto.RoleDto;
import com.finartz.tasksapp.model.entity.RoleEntity;
import com.finartz.tasksapp.model.entity.UserEntity;
import com.finartz.tasksapp.model.exception.UserNotFoundException;
import com.finartz.tasksapp.model.request.AddRoleRequest;
import com.finartz.tasksapp.model.response.GetUserRoleResponse;
import com.finartz.tasksapp.model.response.GetUsersRoleResponse;
import com.finartz.tasksapp.model.response.constant.ErrorLogConstant;
import com.finartz.tasksapp.repository.RoleRepository;
import com.finartz.tasksapp.repository.UserRepository;
import com.finartz.tasksapp.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void addRole(Long id, AddRoleRequest addRoleRequest) throws UserNotFoundException {
        log.debug("Add Role Call Starting");

        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        boolean userIsPresent = userEntityOptional.isPresent();
        if (userIsPresent) {
            addOrUpdateRole(addRoleRequest, userEntityOptional);
        } else {
            log.error(ErrorLogConstant.USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    private void addOrUpdateRole(AddRoleRequest addRoleRequest, Optional<UserEntity> userEntityOptional) {
        Optional<RoleEntity> roleEntityOptional = roleRepository.findById(userEntityOptional.get().getId());

        if (!isRolePresent(roleEntityOptional)) {
            add(addRoleRequest, userEntityOptional);
        } else {
            update(addRoleRequest, roleEntityOptional);
        }
    }

    @Override
    public GetUserRoleResponse getUserRoleByUserId(Long userId) throws UserNotFoundException {
        log.debug("Get User Role By User ID Call Starting");

        Optional<RoleEntity> roleEntityOptional = roleRepository.findByUserId(userId);

        if (isRolePresent(roleEntityOptional)) {
            return getRole(roleEntityOptional.get());
        } else {
            log.error(ErrorLogConstant.USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<GetUsersRoleResponse> getAllUsersRole() {
        log.debug("Get All Users Role Call Starting");

        List<RoleEntity> roleEntities = roleRepository.findAll();
        return getRolesResponses(roleEntities);
    }

    private void add(AddRoleRequest addRoleRequest, Optional<UserEntity> userEntityOptional) {
        log.debug("Add Call Starting");

        RoleDto role = AddRoleRequestConverter.convert(addRoleRequest);
        RoleEntity roleEntity = RoleEntity.builder()
                .user(userEntityOptional.get())
                .type(role.getType()).build();

        roleRepository.save(roleEntity);
    }

    private void update(AddRoleRequest addRoleRequest, Optional<RoleEntity> roleEntityOptional) {
        log.debug("Update Call Starting");

        RoleDto role = AddRoleRequestConverter.convert(addRoleRequest);

        roleEntityOptional.get().setType(role.getType());

        roleRepository.save(roleEntityOptional.get());
    }

    private GetUserRoleResponse getRole(RoleEntity roleEntity) {
        log.debug("Get Role Call Starting");

        return GetUserRoleResponse.builder()
                .name(roleEntity.getUser().getName())
                .surname(roleEntity.getUser().getSurname())
                .type(roleEntity.getType()).build();
    }

    private GetUsersRoleResponse getRoles(RoleEntity roleEntity) {
        log.debug("Get Roles Call Starting");

        return GetUsersRoleResponse.builder()
                .userId(roleEntity.getUser().getId())
                .name(roleEntity.getUser().getName())
                .surname(roleEntity.getUser().getSurname())
                .type(roleEntity.getType()).build();
    }

    private List<GetUsersRoleResponse> getRolesResponses(List<RoleEntity> roleEntities) {
        log.debug("Get Roles Responses Call Starting");

        return roleEntities.stream().map(this::getRoles).collect(Collectors.toList());
    }

    private static boolean isRolePresent(Optional<RoleEntity> roleEntityOptional) {
        return roleEntityOptional.isPresent();
    }
}