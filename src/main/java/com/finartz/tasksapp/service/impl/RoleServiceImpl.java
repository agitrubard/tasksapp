package com.finartz.tasksapp.service.impl;

import com.finartz.tasksapp.model.converter.AddRoleRequestConverter;
import com.finartz.tasksapp.model.dto.RoleDto;
import com.finartz.tasksapp.model.entity.RoleEntity;
import com.finartz.tasksapp.model.entity.UserEntity;
import com.finartz.tasksapp.model.exception.UserNotFoundException;
import com.finartz.tasksapp.model.request.AddRoleRequest;
import com.finartz.tasksapp.model.response.GetUserRoleResponse;
import com.finartz.tasksapp.model.response.GetUsersRoleResponse;
import com.finartz.tasksapp.repository.RoleRepository;
import com.finartz.tasksapp.repository.UserRepository;
import com.finartz.tasksapp.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.finartz.tasksapp.service.impl.UserServiceImpl.isUserPresent;
import static com.finartz.tasksapp.model.response.constant.ErrorLogConstant.USER_NOT_FOUND;

@Service
@AllArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public GetUserRoleResponse addRole(Long userId, AddRoleRequest addRoleRequest) throws UserNotFoundException {
        log.debug("Add Role Call Starting");

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (isUserPresent(userEntityOptional)) {
            return addOrUpdateRole(addRoleRequest, userEntityOptional.get());
        } else {
            log.error(USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public GetUserRoleResponse getUserRoleByUserId(Long userId) throws UserNotFoundException {
        log.debug("Get User Role By User ID Call Starting");

        Optional<RoleEntity> roleEntityOptional = roleRepository.findByUserId(userId);

        if (isRolePresent(roleEntityOptional)) {
            return getRole(roleEntityOptional.get());
        } else {
            log.error(USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<GetUsersRoleResponse> getAllUsersRole() {
        log.debug("Get All Users Role Call Starting");

        List<RoleEntity> roleEntities = roleRepository.findAll();
        return getRolesResponses(roleEntities);
    }

    private GetUserRoleResponse addOrUpdateRole(AddRoleRequest addRoleRequest, UserEntity userEntity) {
        log.debug("Add or Update Role Call Starting");

        Optional<RoleEntity> roleEntityOptional = roleRepository.findById(userEntity.getId());

        if (!isRolePresent(roleEntityOptional)) {
            return add(addRoleRequest, userEntity);
        } else {
            return update(addRoleRequest, roleEntityOptional.get());
        }
    }

    private GetUserRoleResponse add(AddRoleRequest addRoleRequest, UserEntity userEntity) {
        log.debug("Add Call Starting");

        RoleDto role = AddRoleRequestConverter.convert(addRoleRequest);
        RoleEntity roleEntity = RoleEntity.builder()
                .user(userEntity)
                .type(role.getType()).build();

        roleRepository.save(roleEntity);
        return getRole(roleEntity);
    }

    private GetUserRoleResponse update(AddRoleRequest addRoleRequest, RoleEntity roleEntity) {
        log.debug("Update Call Starting");

        RoleDto role = AddRoleRequestConverter.convert(addRoleRequest);

        roleEntity.setType(role.getType());

        roleRepository.save(roleEntity);
        return getRole(roleEntity);
    }

    private GetUserRoleResponse getRole(RoleEntity roleEntity) {
        log.debug("Get Role Call Starting");

        return GetUserRoleResponse.builder()
                .name(roleEntity.getUser().getName())
                .surname(roleEntity.getUser().getSurname())
                .roleType(roleEntity.getType()).build();
    }

    private GetUsersRoleResponse getRoles(RoleEntity roleEntity) {
        log.debug("Get Roles Call Starting");

        return GetUsersRoleResponse.builder()
                .userId(roleEntity.getUser().getId())
                .name(roleEntity.getUser().getName())
                .surname(roleEntity.getUser().getSurname())
                .roleType(roleEntity.getType()).build();
    }

    private List<GetUsersRoleResponse> getRolesResponses(List<RoleEntity> roleEntities) {
        log.debug("Get Roles Responses Call Starting");

        return roleEntities.stream().map(this::getRoles).collect(Collectors.toList());
    }

    private static boolean isRolePresent(Optional<RoleEntity> roleEntityOptional) {
        return roleEntityOptional.isPresent();
    }
}