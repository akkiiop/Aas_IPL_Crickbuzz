package com.akshay.iplcrickbuzz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;

	public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
		this.jwtAuthFilter = jwtAuthFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
					// Public auth endpoints
					.requestMatchers(HttpMethod.POST, "/api/auth/register", "/api/auth/login").permitAll()
					// Swagger / OpenAPI
					.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
					// Public GET reads
					.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
					// ADMIN-only write operations
					.requestMatchers(HttpMethod.POST, "/api/players/**", "/api/teams/**",
							"/api/matches/**", "/api/performances/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.PUT, "/api/players/**", "/api/teams/**",
							"/api/matches/**", "/api/performances/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.DELETE, "/api/players/**", "/api/teams/**",
							"/api/matches/**", "/api/performances/**").hasRole("ADMIN")
					// All other requests require authentication
					.anyRequest().authenticated()
			)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
