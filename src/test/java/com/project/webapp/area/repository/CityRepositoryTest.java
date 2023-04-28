package com.project.webapp.area.repository;


import com.project.webapp.area.entity.City;
import com.project.webapp.area.entity.CityTest;
import com.project.webapp.area.entity.Country;
import com.project.webapp.area.entity.CountryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;


    @Test
    void create() {
        // Given
        City newCity = buildCity();

        // When
        City savedCity = cityRepository.save(newCity);

        // Then
        Optional<City> findByIdOptional = cityRepository.findById(newCity.getCityId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedCity);
    }

    @Test
    void read() {
        // Given
        City newCity = buildCity();
        City savedCity = cityRepository.save(newCity);

        // When
        Optional<City> findByIdOptional = cityRepository.findById(savedCity.getCityId());
        Optional<City> findByCityOptional = cityRepository.findByCity("Abu Dhabi");

        // Then
        assertThat(findByCityOptional).isPresent();
        assertThat(findByCityOptional.get()).isEqualTo(savedCity);

        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedCity);
    }

    @Test
    public void update() {
        // Given
        City newCity = buildCity();
        City savedCity = cityRepository.save(newCity);

        // When
        savedCity.setCity("Adana");
        cityRepository.save(savedCity);

        // Then
        Optional<City> findByIdOptional = cityRepository.findById(savedCity.getCityId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedCity);
    }

    @Test
    void delete() {
        // Given
        City newCity = buildCity();
        City savedCity = cityRepository.save(newCity);

        // When
        cityRepository.delete(savedCity);

        // Then
        Optional<City> findByIdOptional = cityRepository.findById(savedCity.getCityId());
        assertThat(findByIdOptional).isNotPresent();
    }

    private City buildCity() {
        Country newCountry = CountryTest.buildCountry();
        Country savedCountry = countryRepository.save(newCountry);

        return CityTest.buildCity(savedCountry);
    }
}