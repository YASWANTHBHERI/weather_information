package com.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;



@Configuration
public class AppConfig {

	@Value("${api.googleApiKey}")
	private String googleApiKey;
	
	@Value("${api.openWeatherApiKey}")
	private String openWeatherApiKey;
	

	public String getGoogleAPIKey() {
		return googleApiKey;
	}
	
	public String getOpenWeatherAPIKey() {
		return openWeatherApiKey;
	}
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
	
	
}
