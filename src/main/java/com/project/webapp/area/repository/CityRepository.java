package com.project.webapp.area.repository;


import com.project.webapp.area.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository  extends JpaRepository<City, Integer> {
    Optional<City> findByCity(String city);
}