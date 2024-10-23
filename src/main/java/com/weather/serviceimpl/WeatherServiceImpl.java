package com.weather.serviceimpl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.entity.Location;
import com.weather.entity.WeatherData;
import com.weather.exceptionHandler.ResourceNotFoundException;
import com.weather.model.WeatherResponse;
import com.weather.repository.LocationRepository;
import com.weather.repository.WeatherRepository;
import com.weather.service.WeatherApiService;
import com.weather.service.WeatherService;

@Service
public class WeatherServiceImpl implements WeatherService {

	private WeatherRepository weatherRepo;

	private WeatherApiService weatherApiService;

	private ObjectMapper objectMapper;

	private LocationRepository locationRepo;

//	constructor injection
	public WeatherServiceImpl(WeatherRepository weatherRepo, WeatherApiService weatherApiService, ObjectMapper objectMapper,
			LocationRepository locationRepo) {
		this.weatherRepo = weatherRepo;
		this.weatherApiService = weatherApiService;
		this.objectMapper = objectMapper;
		this.locationRepo = locationRepo;
	}
	

	@Override
	public String getWeatherByPincodeAndDate(String pincode, String dateInSeconds) {

		WeatherData existingWeatherData = weatherRepo.findByPincodeAndDateInSeconds(pincode, dateInSeconds);
		if (existingWeatherData != null) {
			return existingWeatherData.getWeatherResponse();
		}
		WeatherResponse weatherResponse = weatherApiService.fetchWeatherByPincode(pincode, dateInSeconds);
		String weatherResponseJson = convertObjectToJson(weatherResponse);

//		getting the location id of pincode
		Location location = locationRepo.findByPincode(pincode).orElseThrow(()->new ResourceNotFoundException());

				WeatherData newWeatherData = new WeatherData();
		String id = UUID.randomUUID().toString();
		newWeatherData.setId(id);
		newWeatherData.setPincode(pincode);
		newWeatherData.setWeatherResponse(weatherResponseJson);
		newWeatherData.setDateInSeconds(dateInSeconds.toString());
		newWeatherData.setLocation(location);
		weatherRepo.save(newWeatherData);

		return weatherResponseJson;
	}



	private String convertObjectToJson(WeatherResponse weatherResponse) {
		try {

			return objectMapper.writeValueAsString(weatherResponse);

		} catch (Exception e) {
			throw new ResourceNotFoundException("Unable to Convert Weather response to JSON");
		}
	}

}
