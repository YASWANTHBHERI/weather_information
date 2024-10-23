package com.weather.service;

import com.weather.model.WeatherResponse;

public interface WeatherApiService {
	
	public WeatherResponse fetchWeatherByPincode(String pincode, String dateInSeconds);
	

}
