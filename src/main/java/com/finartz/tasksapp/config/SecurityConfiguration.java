package com.finartz.tasksapp.config;

import com.finartz.tasksapp.controller.endpoint.MetricControllerEndpoint;
import com.finartz.tasksapp.controller.endpoint.RoleControllerEndpoint;
import com.finartz.tasksapp.controller.endpoint.UserControllerEndpoint;
import com.finartz.tasksapp.model.enums.RoleType;
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

@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailService userDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(UserControllerEndpoint.SIGN_UP).permitAll()
                .antMatchers(
                        UserControllerEndpoint.LOG_IN,
                        MetricControllerEndpoint.CREATE_METRIC_BY_USER_ID,
                        MetricControllerEndpoint.METRIC_BY_USER_ID_AND_METRIC_ID,
                        MetricControllerEndpoint.GET_METRICS_BY_USER_ID).hasAnyRole(RoleType.TEAM_LEAD.name(), RoleType.DEVELOPER.name())
                .antMatchers(
                        UserControllerEndpoint.USER,
                        UserControllerEndpoint.USER_ID,
                        MetricControllerEndpoint.GET_METRICS,
                        RoleControllerEndpoint.ROLE_BY_USER_ID,
                        RoleControllerEndpoint.GET_USERS_ROLE).hasAnyRole(RoleType.TEAM_LEAD.name())
                .anyRequest().authenticated()
                .and().httpBasic();
    }
}