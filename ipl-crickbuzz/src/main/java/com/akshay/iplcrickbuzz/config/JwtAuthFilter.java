package com.akshay.iplcrickbuzz.config;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	public JwtAuthFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String token = authHeader.substring(7);

			if (jwtUtil.isTokenValid(token)) {
				String username = jwtUtil.extractUsername(token);
				String role = jwtUtil.extractRole(token);

				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(
								username,
								null,
								List.of(new SimpleGrantedAuthority("ROLE_" + role))
						);

				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				// Token was provided but is invalid/expired → return 401
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);

				String json = "{\"timestamp\":\"" + java.time.LocalDateTime.now() + "\","
						+ "\"status\":401,"
						+ "\"message\":\"Invalid or expired JWT token\","
						+ "\"path\":\"" + request.getRequestURI() + "\"}";

				response.getWriter().write(json);
				return; // Don't continue the filter chain
			}
		}

		filterChain.doFilter(request, response);
	}
}
