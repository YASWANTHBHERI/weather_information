package com.weather.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weather.helper.DateTimeHelper;
import com.weather.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

	private static Logger logger = LoggerFactory.getLogger(WeatherController.class);

	private DateTimeHelper dateTimeHelper = new DateTimeHelper();
	
	private WeatherService weatherService;
	
//	constructor injection
	public WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@GetMapping("/{pincode}/{forDate}")
	public ResponseEntity<String> getWeather(@PathVariable String pincode, @PathVariable String forDate) {
		
		String requestedDateInSeconds = dateTimeHelper.getSecondsOfDate(forDate).toString();
		logger.info("Requested Date {}: Seconds{}",forDate,requestedDateInSeconds);
		String weatherData = weatherService.getWeatherByPincodeAndDate(pincode, requestedDateInSeconds);
		return ResponseEntity.ok(weatherData);
	}

}
