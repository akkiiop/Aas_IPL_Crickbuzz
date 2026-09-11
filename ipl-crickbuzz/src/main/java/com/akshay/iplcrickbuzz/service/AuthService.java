package com.akshay.iplcrickbuzz.service;

import com.akshay.iplcrickbuzz.dto.AuthResponseDTO;
import com.akshay.iplcrickbuzz.dto.LoginRequestDTO;
import com.akshay.iplcrickbuzz.dto.RegisterRequestDTO;

public interface AuthService {

	AuthResponseDTO register(RegisterRequestDTO request);

	AuthResponseDTO login(LoginRequestDTO request);
}
