package com.project.webapp.area.repository;

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
class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void create() {
        // Given
        Country newCountry = CountryTest.buildCountry();

        // When
        Country savedCountry = countryRepository.save(newCountry);

        // Then
        Optional<Country> findByIdOptional = countryRepository.findById(savedCountry.getCountryId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedCountry);
    }

    @Test
    public void read() {
        // Given
        Country newCountry = CountryTest.buildCountry();
        Country savedCountry = countryRepository.save(newCountry);

        // When
        Optional<Country> findByIdOptional = countryRepository.findById(savedCountry.getCountryId());
        Optional<Country> findByCountryOptional = countryRepository.findByCountry("Afghanistan");

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedCountry);

        assertThat(findByCountryOptional).isPresent();
        assertThat(findByCountryOptional.get()).isEqualTo(savedCountry);
    }

    @Test
    public void update() {
        // Given
        Country newCountry = CountryTest.buildCountry();
        Country savedCountry = countryRepository.save(newCountry);

        // When
        savedCountry.setCountry("South Korea");
        countryRepository.save(savedCountry);

        // Then
        Optional<Country> findByIdOptional = countryRepository.findById(savedCountry.getCountryId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedCountry);
    }

    @Test
    public void delete() {
        // Given
        Country newCountry = CountryTest.buildCountry();
        Country savedCountry = countryRepository.save(newCountry);

        // When
        countryRepository.deleteById(savedCountry.getCountryId());

        // Then
        Optional<Country> findByIdOptional = countryRepository.findById(savedCountry.getCountryId());
        assertThat(findByIdOptional).isNotPresent();
    }
}