package com.finartz.tasksapp.service.impl;

import com.finartz.tasksapp.model.entity.RoleEntity;
import com.finartz.tasksapp.model.entity.UserEntity;
import com.finartz.tasksapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Load User By Username Call Starting");

        Optional<UserEntity> user = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),
                getAuthorities(user));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Optional<UserEntity> user) {
        log.debug("Get Authorities Call Starting");

        RoleEntity userRole = user.get().getRole();
        if (userRole != null) {
            return AuthorityUtils.createAuthorityList("ROLE_" + userRole.getType().name());
        }
        return Collections.emptyList();
    }
}