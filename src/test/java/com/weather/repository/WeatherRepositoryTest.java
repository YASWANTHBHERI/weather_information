package com.weather.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.weather.entity.WeatherData;
import com.weather.serviceimpl.WeatherServiceImpl;

public class WeatherRepositoryTest {

    @Mock
    private WeatherRepository weatherRepository; // Mock the WeatherRepository

    @InjectMocks
    private WeatherServiceImpl weatherService;

    private WeatherData mockWeatherData;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockWeatherData = new WeatherData();
        mockWeatherData.setId("1");
        mockWeatherData.setPincode("600001");
        mockWeatherData.setDateInSeconds("1697932800");
        mockWeatherData.setWeatherResponse("Sample Weather Response");
    }

    @Test
    public void testFindByPincodeAndDateInSeconds() {
        // Arrange
        String pincode = "600001";
        String dateInSeconds = "1697932800";
        when(weatherRepository.findByPincodeAndDateInSeconds(pincode, dateInSeconds)).thenReturn(mockWeatherData);

        // Act
        WeatherData result = weatherRepository.findByPincodeAndDateInSeconds(pincode, dateInSeconds);

        // Assert
        assertEquals(mockWeatherData.getId(), result.getId());
        assertEquals(mockWeatherData.getPincode(), result.getPincode());
        assertEquals(mockWeatherData.getDateInSeconds(), result.getDateInSeconds());
        assertEquals(mockWeatherData.getWeatherResponse(), result.getWeatherResponse());
    }
}
