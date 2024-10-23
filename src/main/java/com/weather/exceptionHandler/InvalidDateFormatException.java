package com.weather.exceptionHandler;

public class InvalidDateFormatException extends RuntimeException {
	 public InvalidDateFormatException(String message) {
	        super(message);
	    }
}
