package com.finartz.tasksapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finartz.tasksapp.model.request.SignupRequest;
import com.finartz.tasksapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static com.finartz.tasksapp.controller.endpoint.UserControllerEndpoint.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void givenSignupRequest_whenCallSignUp_thenReturn200() throws Exception {
        // Given
        SignupRequest signupRequest = SignupRequest.builder()
                .name("Test-Name")
                .surname("Test-Surname")
                .email("test@email.com")
                .password("123-Test").build();

        // When
        ResultActions actions = mockMvc.perform(post(SIGN_UP)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)));

        // Then
        ArgumentCaptor<SignupRequest> captor = ArgumentCaptor.forClass(SignupRequest.class);
        verify(userService, times(1))
                .createUser(captor.capture());

        assertThat(captor.getValue().getName()).isEqualTo(signupRequest.getName());
        assertThat(captor.getValue().getSurname()).isEqualTo(signupRequest.getSurname());
        assertThat(captor.getValue().getEmail()).isEqualTo(signupRequest.getEmail());
        assertThat(captor.getValue().getPassword()).isEqualTo(signupRequest.getPassword());
        actions.andExpect(status().isOk());
    }
}