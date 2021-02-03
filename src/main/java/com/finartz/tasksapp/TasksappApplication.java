package com.finartz.tasksapp;

import com.finartz.tasksapp.model.entity.RoleEntity;
import com.finartz.tasksapp.model.entity.UserEntity;
import com.finartz.tasksapp.model.enums.RoleType;
import com.finartz.tasksapp.repository.RoleRepository;
import com.finartz.tasksapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@AllArgsConstructor
@Slf4j
public class TasksappApplication {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        SpringApplication.run(TasksappApplication.class, args);
    }

    @PostConstruct
    private void addTeamLead() {
        log.debug("Add Team Lead Call Starting");

        UserEntity user = saveAdmin();

        addTeamLeadRole(user);
    }

    private UserEntity saveAdmin() {
        log.debug("Save Admin Call Starting");

        UserEntity user = new UserEntity();

        user.setName("Admin");
        user.setSurname("Finartz");
        user.setEmail("admin@tasksapp.com");
        user.setPassword(encoder.encode("123456"));

        return userRepository.save(user);
    }

    private void addTeamLeadRole(UserEntity user) {
        log.debug("Add Team Lead Role Call Starting");

        RoleEntity role = new RoleEntity();

        role.setUser(user);
        role.setType(RoleType.TEAM_LEAD);

        roleRepository.save(role);
    }
}