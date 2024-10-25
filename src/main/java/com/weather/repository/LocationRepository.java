package com.weather.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.weather.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, String> {
	
	Optional<Location>findByPincode(String pincode);

}
