package com.weather.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="weather_data")
public class WeatherData {
	
	@Id
	private String id;
	
	private String pincode;
	
	private String dateInSeconds;
	
	
	@Lob
	@Column(name="weather_response",columnDefinition = "TEXT")
	private String weatherResponse;
	
	@ManyToOne
	@JoinColumn(name="location_id",nullable = false)
	private Location location;
	
	

}
