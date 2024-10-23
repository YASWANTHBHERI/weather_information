package com.weather.model;

import java.util.List;

import lombok.Data;

@Data
public class WeatherResponse {
	
	private String cod;
	private int city_id;
	private double calctime;
	private int cnt;
	private List<WeatherItem>list;
	
	@Data
	public static class WeatherItem{
		private long dt;
		private Main main;
		private Wind wind;
		private Clouds clouds;
		private List<Weather>weather;
	}
	
	@Data
	public static class Main{
		private double temp;
		private double feels_like;
		private int pressure;
		private int humidity;
		private double temp_min;
		private double temp_max;
	}
	
	@Data
	public static class Wind{
		private double temp;
		private int deg;
		private double gust;
	}
	
	@Data
	public static class Clouds{
		private int all;
	}
	
	@Data
	public static class Weather{
		private int id;
		private String main;
		private String description;
		private String icon;
	}

}
