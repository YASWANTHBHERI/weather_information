package com.weather.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.entity.Location;
import com.weather.entity.WeatherData;
import com.weather.model.WeatherResponse;
import com.weather.repository.LocationRepository;
import com.weather.repository.WeatherRepository;
import com.weather.service.WeatherApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

public class WeatherServiceImplTest {

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @Mock
    private WeatherRepository weatherRepo;

    @Mock
    private WeatherApiService weatherApiService;

    @Mock
    private LocationRepository locationRepo;

    @Mock
    private ObjectMapper objectMapper;

    private WeatherData mockWeatherData;
    private WeatherResponse mockWeatherResponse;
    private Location mockLocation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockWeatherData = new WeatherData();
        mockWeatherData.setId(UUID.randomUUID().toString());
        mockWeatherData.setPincode("123456");
        mockWeatherData.setDateInSeconds("1234567890");
        mockWeatherData.setWeatherResponse("{\"weather\":\"sunny\"}");

        mockWeatherResponse = new WeatherResponse();
        mockLocation = new Location();
        mockLocation.setPincode("123456");
        mockLocation.setLocationId(UUID.randomUUID().toString());
    }

    @Test
    public void testGetWeatherByPincodeAndDate_WhenDataExists() {
        // Mock behavior for repository
        when(weatherRepo.findByPincodeAndDateInSeconds(anyString(), anyString())).thenReturn(mockWeatherData);

        // Test
        String result = weatherService.getWeatherByPincodeAndDate("123456", "1234567890");

        // Verify
        assertEquals("{\"weather\":\"sunny\"}", result);
        verify(weatherRepo, times(1)).findByPincodeAndDateInSeconds(anyString(), anyString());
        verifyNoMoreInteractions(weatherApiService, objectMapper, locationRepo, weatherRepo);
    }

    @Test
    public void testGetWeatherByPincodeAndDate_WhenDataDoesNotExist() throws Exception {
        // Mock data does not exist
        when(weatherRepo.findByPincodeAndDateInSeconds(anyString(), anyString())).thenReturn(null);
        when(weatherApiService.fetchWeatherByPincode(anyString(), anyString())).thenReturn(mockWeatherResponse);
        when(locationRepo.findByPincode(anyString())).thenReturn(Optional.of(mockLocation));
        when(objectMapper.writeValueAsString(any(WeatherResponse.class))).thenReturn("{\"weather\":\"sunny\"}");

        // Test
        String result = weatherService.getWeatherByPincodeAndDate("123456", "1234567890");

        // Verify
        assertEquals("{\"weather\":\"sunny\"}", result);
        verify(weatherRepo, times(1)).findByPincodeAndDateInSeconds(anyString(), anyString());
        verify(weatherApiService, times(1)).fetchWeatherByPincode(anyString(), anyString());
        verify(locationRepo, times(1)).findByPincode(anyString());
        verify(weatherRepo, times(1)).save(any(WeatherData.class));
    }

}
