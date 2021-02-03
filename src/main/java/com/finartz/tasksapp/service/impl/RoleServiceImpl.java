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
        log.info("Add Role Call Starting");

        Optional<UserEntity> userEntityOptional = userRepository.findById(id);

        boolean userIsPresent = userEntityOptional.isPresent();
        if (userIsPresent) {
            Optional<RoleEntity> roleEntityOptional = roleRepository.findById(userEntityOptional.get().getId());

            boolean roleIsPresent = roleEntityOptional.isPresent();
            if (!roleIsPresent) {
                add(addRoleRequest, userEntityOptional);
            } else {
                update(addRoleRequest, roleEntityOptional);
            }
        } else {
            log.error(ErrorLogConstant.USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public GetUserRoleResponse getUserRoleByUserId(Long userId) throws UserNotFoundException {
        log.info("Get User Role By User ID Call Starting");

        Optional<RoleEntity> roleEntityOptional = roleRepository.findByUserId(userId);

        boolean userIsPresent = roleEntityOptional.isPresent();
        if (userIsPresent) {
            return getRole(roleEntityOptional.get());
        } else {
            log.error(ErrorLogConstant.USER_NOT_FOUND);
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<GetUsersRoleResponse> getAllUsersRole() {
        log.info("Get All Users Role Call Starting");

        List<RoleEntity> roleEntities = roleRepository.findAll();
        return getRolesResponses(roleEntities);
    }

    private void add(AddRoleRequest addRoleRequest, Optional<UserEntity> userEntityOptional) {
        log.info("Add Call Starting");

        RoleDto role = AddRoleRequestConverter.convert(addRoleRequest);
        RoleEntity roleEntity = new RoleEntity();

        roleEntity.setUser(userEntityOptional.get());
        roleEntity.setType(role.getType());

        roleRepository.save(roleEntity);
    }

    private void update(AddRoleRequest addRoleRequest, Optional<RoleEntity> roleEntityOptional) {
        log.info("Update Call Starting");

        RoleDto role = AddRoleRequestConverter.convert(addRoleRequest);

        roleEntityOptional.get().setType(role.getType());

        roleRepository.save(roleEntityOptional.get());
    }

    private GetUserRoleResponse getRole(RoleEntity roleEntity) {
        log.info("Get Role Call Starting");

        GetUserRoleResponse getUserRoleResponse = new GetUserRoleResponse();

        getUserRoleResponse.setName(roleEntity.getUser().getName());
        getUserRoleResponse.setSurname(roleEntity.getUser().getSurname());
        getUserRoleResponse.setType(roleEntity.getType());

        return getUserRoleResponse;
    }

    private GetUsersRoleResponse getRoles(RoleEntity roleEntity) {
        log.info("Get Roles Call Starting");

        GetUsersRoleResponse getUsersRoleResponse = new GetUsersRoleResponse();

        getUsersRoleResponse.setUserId(roleEntity.getUser().getId());
        getUsersRoleResponse.setName(roleEntity.getUser().getName());
        getUsersRoleResponse.setSurname(roleEntity.getUser().getSurname());
        getUsersRoleResponse.setType(roleEntity.getType());

        return getUsersRoleResponse;
    }


    private List<GetUsersRoleResponse> getRolesResponses(List<RoleEntity> roleEntities) {
        log.info("Get Roles Responses Call Starting");

        return roleEntities.stream().map(this::getRoles).collect(Collectors.toList());
    }
}