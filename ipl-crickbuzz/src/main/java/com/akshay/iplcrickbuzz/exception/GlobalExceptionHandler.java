package com.akshay.iplcrickbuzz.exception;
import com.akshay.iplcrickbuzz.exception.MatchNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.akshay.iplcrickbuzz.dto.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
		
	@ExceptionHandler(PlayerNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handlePlayerNotFound(
	        PlayerNotFoundException ex,
	        HttpServletRequest request) {

	    ErrorResponseDTO error = new ErrorResponseDTO(
	            LocalDateTime.now(),
	            HttpStatus.NOT_FOUND.value(),
	            ex.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity
	            .status(HttpStatus.NOT_FOUND)
	            .body(error);
	}
		
		
		
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDTO> handleValidationException(
	        MethodArgumentNotValidException ex,
	        HttpServletRequest request) {

	    String message = ex.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .map(error ->
	                    error.getField() + ": " + error.getDefaultMessage())
	            .findFirst()
	            .orElse("Validation failed");

	    ErrorResponseDTO error = new ErrorResponseDTO(
	            LocalDateTime.now(),
	            HttpStatus.BAD_REQUEST.value(),
	            message,
	            request.getRequestURI()
	    );

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(error);
	}
		
		
		@ExceptionHandler(TeamNotFoundException.class)
		public ResponseEntity<ErrorResponseDTO> handleTeamNotFound(
		        TeamNotFoundException ex,
		        HttpServletRequest request) {

		    ErrorResponseDTO error = new ErrorResponseDTO(
		            LocalDateTime.now(),
		            HttpStatus.NOT_FOUND.value(),
		            ex.getMessage(),
		            request.getRequestURI()
		    );

		    return ResponseEntity
		            .status(HttpStatus.NOT_FOUND)
		            .body(error);
		}
		
		
		@ExceptionHandler(MatchNotFoundException.class)
		public ResponseEntity<ErrorResponseDTO> handleMatchNotFoundException(
		        MatchNotFoundException ex, 
		        HttpServletRequest request) {

		    ErrorResponseDTO errorResponse = new ErrorResponseDTO(
		            LocalDateTime.now(),
		            HttpStatus.NOT_FOUND.value(),
		            "Not Found",
		            ex.getMessage(),
		            request.getRequestURI()
		    );

		    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
		}

	
}
