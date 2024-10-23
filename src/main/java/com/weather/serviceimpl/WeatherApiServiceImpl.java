package com.weather.serviceimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weather.config.AppConfig;
import com.weather.entity.Location;
import com.weather.exceptionHandler.ExternalApiException;
import com.weather.exceptionHandler.ResourceNotFoundException;
import com.weather.helper.DateTimeHelper;
import com.weather.helper.JsonResponseFormatterHelper;
import com.weather.model.WeatherResponse;
import com.weather.repository.LocationRepository;
import com.weather.service.WeatherApiService;

@Service
public class WeatherApiServiceImpl implements WeatherApiService {

	private AppConfig appConfig;

	private RestTemplate restTemplate;

	private LocationRepository locationRepo;
	
	private static Logger logger = LoggerFactory.getLogger(WeatherApiServiceImpl.class);

//	constructor injection
	public WeatherApiServiceImpl(AppConfig appConfig, RestTemplate restTemplate, LocationRepository locationRepo) {
		this.appConfig = appConfig;
		this.restTemplate = restTemplate;
		this.locationRepo = locationRepo;
	}

	private Location getLatitudeAndLongitude(String pincode) {
		JsonResponseFormatterHelper jsonFormatHelper = new JsonResponseFormatterHelper();
		return locationRepo.findByPincode(pincode).orElseGet(() -> {

			try {
				String locationApiKey = appConfig.getGoogleAPIKey();
				String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + pincode + "&key="
						+ locationApiKey;

				String response = restTemplate.getForObject(apiUrl, String.class);
				
				if (response == null || response.isEmpty()) {
					throw new ResourceNotFoundException("Google Maps API returned no data for pincode: " + pincode);
				}
				
				Location newLocation = jsonFormatHelper.getLocationObject(response, pincode);

				return locationRepo.save(newLocation);

			} catch (Exception e) {
				logger.error("Error calling OpenWeather API", e);
				throw new ResourceNotFoundException("Invalid Pincode No Data Found: " + pincode);
			}

		});

	}

	@Override
	public WeatherResponse fetchWeatherByPincode(String pincode, String dateInSeconds) {
		Location location = getLatitudeAndLongitude(pincode);
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();

		DateTimeHelper dateTimeHelper = new DateTimeHelper();
		String endTime = dateTimeHelper.getEndSecondsOfDate(dateInSeconds);

		String weatherApiKey = appConfig.getOpenWeatherAPIKey();
		
		try {
			
			String apiUrl = "https://history.openweathermap.org/data/2.5/history/city?lat=" + latitude + "&lon=" + longitude
					+ "&type=hour&start=" + dateInSeconds + "&end=" + endTime + "&units=metric&appid=" + weatherApiKey;
			WeatherResponse weatherResponse = restTemplate.getForObject(apiUrl, WeatherResponse.class);
			if(weatherResponse==null) {
				throw new ExternalApiException("no data for location: (" + latitude + ", " + longitude + ")");
			}
			return weatherResponse;
			
		} catch (Exception e) {
			logger.error("Error calling OpenWeather API", e);
			throw new ExternalApiException("Failed to retrieve weather data for pincode: " + pincode, e);
		}

		

	}

}
