package com.weather.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weather.entity.WeatherData;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherData, String> {
	WeatherData findByPincodeAndDateInSeconds(String pincode, String dateInSeconds);
}
