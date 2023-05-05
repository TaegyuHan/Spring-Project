package com.project.webapp.area.repository;

import com.project.webapp.area.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface  CountryRepository extends JpaRepository<Country, Integer> {

    Optional<Country> findByCountry(String country);
    
    List<Country> findAll();

    boolean existsByCountry(String country);
}