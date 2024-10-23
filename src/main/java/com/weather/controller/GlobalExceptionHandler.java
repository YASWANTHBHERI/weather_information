package com.weather.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.weather.exceptionCustomResponse.CustomErrorResponse;
import com.weather.exceptionHandler.ExternalApiException;
import com.weather.exceptionHandler.InvalidDateFormatException;
import com.weather.exceptionHandler.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(InvalidDateFormatException.class)
	public ResponseEntity<CustomErrorResponse> handleInvalidDateFormatException(InvalidDateFormatException ex, WebRequest request) {
	    CustomErrorResponse errorResponse = new CustomErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.BAD_REQUEST.value(),
	            "Bad Request",
	            ex.getMessage(),
	            request.getDescription(false)
	    );
	    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<CustomErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
	    CustomErrorResponse errorResponse = new CustomErrorResponse(
	            LocalDateTime.now(),
	            HttpStatus.NOT_FOUND.value(),
	            "Not Found",
	            ex.getMessage(),
	            request.getDescription(false)
	    );
	    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ExternalApiException.class)
	public ResponseEntity<CustomErrorResponse> handleExternalApiException(ExternalApiException ex, WebRequest request) {
	    CustomErrorResponse errorResponse = new CustomErrorResponse(
	        LocalDateTime.now(),
	        HttpStatus.SERVICE_UNAVAILABLE.value(),
	        HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase(),
	        ex.getMessage(),
	        request.getDescription(false)
	    );
	    
	    return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}

}
