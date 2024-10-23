package com.weather.controller;

import com.weather.helper.DateTimeHelper;
import com.weather.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class WeatherControllerTest {

    @InjectMocks
    private WeatherController weatherController;

    @Mock
    private WeatherService weatherService;

    private DateTimeHelper dateTimeHelper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        dateTimeHelper = new DateTimeHelper();
    }

    @Test
    public void testGetWeather() throws Exception {
        // Given
        String pincode = "600001"; // Example pincode
        String validDate = "21-10-2023"; // Date in dd-MM-yyyy format
        String expectedWeatherResponse = "Weather Data"; // Example expected response

        // Mocking the service response
        when(weatherService.getWeatherByPincodeAndDate(anyString(), anyString()))
                .thenReturn(expectedWeatherResponse);

        // When
        MockMvc mockMvc = standaloneSetup(weatherController).build();
        mockMvc.perform(get("/api/weather/{pincode}/{forDate}", pincode, validDate))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedWeatherResponse));
    }
}
