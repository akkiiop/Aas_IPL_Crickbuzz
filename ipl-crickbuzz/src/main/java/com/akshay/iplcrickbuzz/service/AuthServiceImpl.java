package com.akshay.iplcrickbuzz.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.akshay.iplcrickbuzz.config.JwtUtil;
import com.akshay.iplcrickbuzz.dto.AuthResponseDTO;
import com.akshay.iplcrickbuzz.dto.LoginRequestDTO;
import com.akshay.iplcrickbuzz.dto.RegisterRequestDTO;
import com.akshay.iplcrickbuzz.entity.User;
import com.akshay.iplcrickbuzz.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public AuthServiceImpl(UserRepository userRepository,
						   BCryptPasswordEncoder passwordEncoder,
						   JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public AuthResponseDTO register(RegisterRequestDTO request) {
		// 1. Check if username already exists
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new RuntimeException("Username '" + request.getUsername() + "' is already taken");
		}

		// 2. Create user with encoded password
		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole("USER");

		// 3. Save user
		userRepository.save(user);

		// 4. Generate JWT token
		String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

		// 5. Return response
		return new AuthResponseDTO(token, user.getUsername(), user.getRole());
	}

	@Override
	public AuthResponseDTO login(LoginRequestDTO request) {
		// 1. Find user by username
		User user = userRepository.findByUsername(request.getUsername())
				.orElseThrow(() -> new RuntimeException("Invalid username or password"));

		// 2. Verify password
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid username or password");
		}

		// 3. Generate JWT token
		String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

		// 4. Return response
		return new AuthResponseDTO(token, user.getUsername(), user.getRole());
	}
}
