package com.weather.helper;

import java.util.UUID;

import org.json.JSONObject;

import com.weather.entity.Location;

public class JsonResponseFormatterHelper {


	public Location getLocationObject(String response, String pincode) {

		JSONObject jsonResponse = new JSONObject(response);
		double latitude = jsonResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry")
				.getJSONObject("location").getDouble("lat");

		double longitude = jsonResponse.getJSONArray("results").getJSONObject(0).getJSONObject("geometry")
				.getJSONObject("location").getDouble("lng");

		Location location = new Location();
		String id = UUID.randomUUID().toString();
		location.setLocationId(id);
		location.setPincode(pincode);
		location.setLatitude(latitude);
		location.setLongitude(longitude);

		return location;

	}

}
