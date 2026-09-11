package com.akshay.iplcrickbuzz.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akshay.iplcrickbuzz.dto.AuthResponseDTO;
import com.akshay.iplcrickbuzz.dto.LoginRequestDTO;
import com.akshay.iplcrickbuzz.dto.RegisterRequestDTO;
import com.akshay.iplcrickbuzz.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register")
	public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
		return ResponseEntity.ok(authService.login(request));
	}
}
