package com.akshay.iplcrickbuzz.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.akshay.iplcrickbuzz.config.JwtUtil;
import com.akshay.iplcrickbuzz.dto.AuthResponseDTO;
import com.akshay.iplcrickbuzz.dto.LoginRequestDTO;
import com.akshay.iplcrickbuzz.dto.RegisterRequestDTO;
import com.akshay.iplcrickbuzz.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtUtil jwtUtil;

    // TEST 1: Register Success
    @Test
    void register_Success() throws Exception {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("newuser");
        request.setPassword("password123");

        AuthResponseDTO response = new AuthResponseDTO("jwt-token-123", "newuser", "USER");

        when(authService.register(any(RegisterRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token-123"))
                .andExpect(jsonPath("$.username").value("newuser"))
                .andExpect(jsonPath("$.role").value("USER"));
    }

    // TEST 2: Login Success
    @Test
    void login_Success() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("admin1");
        request.setPassword("admin123");

        AuthResponseDTO response = new AuthResponseDTO("admin-jwt-token", "admin1", "ADMIN");

        when(authService.login(any(LoginRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("admin-jwt-token"))
                .andExpect(jsonPath("$.username").value("admin1"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    // TEST 3: Login Invalid Credentials
    @Test
    void login_InvalidCredentials_ThrowsException() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("wronguser");
        request.setPassword("wrongpass");

        when(authService.login(any(LoginRequestDTO.class)))
                .thenThrow(new RuntimeException("Invalid username or password"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
