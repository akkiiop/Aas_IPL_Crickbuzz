package com.akshay.iplcrickbuzz.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.akshay.iplcrickbuzz.config.JwtUtil;
import com.akshay.iplcrickbuzz.dto.AuthResponseDTO;
import com.akshay.iplcrickbuzz.dto.LoginRequestDTO;
import com.akshay.iplcrickbuzz.dto.RegisterRequestDTO;
import com.akshay.iplcrickbuzz.entity.User;
import com.akshay.iplcrickbuzz.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    // TEST 1: Register Success
    @Test
    void register_Success() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("testuser");
        request.setPassword("password123");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(jwtUtil.generateToken("testuser", "USER")).thenReturn("fake-jwt-token");

        AuthResponseDTO response = authService.register(request);

        assertEquals("testuser", response.getUsername());
        assertEquals("USER", response.getRole());
        assertEquals("fake-jwt-token", response.getToken());

        verify(userRepository).save(any(User.class));
    }

    // TEST 2: Register Duplicate Username
    @Test
    void register_DuplicateUsername_ThrowsException() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("existinguser");
        request.setPassword("password123");

        when(userRepository.existsByUsername("existinguser")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.register(request);
        });

        assertTrue(exception.getMessage().contains("already taken"));
        verify(userRepository, never()).save(any(User.class));
    }

    // TEST 3: Login Success
    @Test
    void login_Success() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("testuser");
        request.setPassword("password123");

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        user.setRole("USER");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken("testuser", "USER")).thenReturn("fake-jwt-token");

        AuthResponseDTO response = authService.login(request);

        assertEquals("testuser", response.getUsername());
        assertEquals("fake-jwt-token", response.getToken());
    }

    // TEST 4: Login User Not Found
    @Test
    void login_UserNotFound_ThrowsException() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("unknownuser");
        request.setPassword("password123");

        when(userRepository.findByUsername("unknownuser")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });
    }

    // TEST 5: Login Wrong Password
    @Test
    void login_WrongPassword_ThrowsException() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("testuser");
        request.setPassword("wrongpassword");

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });
    }
}
