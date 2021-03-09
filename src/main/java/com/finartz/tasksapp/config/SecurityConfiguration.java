package com.finartz.tasksapp.config;

import com.finartz.tasksapp.repository.UserRepository;
import com.finartz.tasksapp.service.impl.CustomUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.finartz.tasksapp.controller.endpoint.UserControllerEndpoint.*;
import static com.finartz.tasksapp.controller.endpoint.MetricControllerEndpoint.*;
import static com.finartz.tasksapp.controller.endpoint.RoleControllerEndpoint.*;
import static com.finartz.tasksapp.model.enums.RoleType.*;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService userDetailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(SIGN_UP).permitAll()
                .antMatchers(LOG_IN,
                        CREATE_METRIC_BY_USER_ID,
                        METRIC_BY_METRIC_ID,
                        GET_METRICS_BY_USER_ID).hasAnyRole(TEAM_LEAD.name(), DEVELOPER.name())
                .antMatchers(
                        USER,
                        USER_ID,
                        GET_METRICS,
                        ROLE_BY_USER_ID,
                        GET_USERS_ROLE).hasAnyRole(TEAM_LEAD.name())
                .anyRequest().authenticated()
                .and().httpBasic();
    }
}