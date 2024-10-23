package com.weather.helper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.weather.exceptionHandler.*;

public class DateTimeHelper {
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	
	public Long getSecondsOfDate(String date) {
		try {
			
			LocalDate requestedDate = LocalDate.parse(date,formatter);
			
//			covert Date to time
			Instant instant = requestedDate.atStartOfDay(ZoneId.of("UTC")).toInstant();
			long seconds = instant.getEpochSecond();
			
			return seconds;
			
		} catch (Exception e) {
//			e.printStackTrace();
			throw new InvalidDateFormatException("Invalid date format: " + date + ". Please use dd-MM-yyyy format.");
		}

	}
	
	public String getEndSecondsOfDate(String startSeconds) {
		if(startSeconds==null) return null;
		try {
			long startSecondsLong = Long.parseLong(startSeconds);
			Instant startInstant = Instant.ofEpochSecond(startSecondsLong);
			LocalDate reqDate = startInstant.atZone(ZoneId.of("UTC")).toLocalDate();
			Instant endInstant = reqDate.plusDays(1).atStartOfDay(ZoneId.of("UTC")).minusSeconds(1).toInstant();
			String endTime = String.valueOf(endInstant.getEpochSecond());
			
			return endTime;
		} catch (Exception e) {
//			e.printStackTrace();
			throw new InvalidDateFormatException("Invalid start seconds: " + startSeconds);
		}
	}
	
}
