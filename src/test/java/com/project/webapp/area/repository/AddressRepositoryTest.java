package com.project.webapp.area.repository;

import com.project.webapp.area.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AddressRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void create() {
        // Given
        Address newAddress = buildAddress();

        // When
        Address savedAddress = addressRepository.save(newAddress);

        // Then
        Optional<Address> findByIdOptional = addressRepository.findById(savedAddress.getAddressId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedAddress);
    }

    @Test
    public void read() {
        // Given
        Address newAddress = buildAddress();
        Address savedAddress = addressRepository.save(newAddress);

        // When
        Optional<Address> findByIdOptional = addressRepository.findById(newAddress.getAddressId());
        Optional<Address> findByAddressOptional = addressRepository.findByAddress(newAddress.getAddress());
        Optional<Address> findByAddress2Optional = addressRepository.findByAddress2(newAddress.getAddress2());
        Optional<Address> findByDistrictOptional = addressRepository.findByDistrict(newAddress.getDistrict());
        Optional<Address> findByCityOptional = addressRepository.findByCity(newAddress.getCity());
        Optional<Address> findByPostalCodeOptional = addressRepository.findByPostalCode(newAddress.getPostalCode());
        Optional<Address> findByPhoneOptional = addressRepository.findByPhone(newAddress.getPhone());

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedAddress);

        assertThat(findByAddressOptional).isPresent();
        assertThat(findByAddressOptional.get()).isEqualTo(savedAddress);

        assertThat(findByAddress2Optional).isPresent();
        assertThat(findByAddress2Optional.get()).isEqualTo(savedAddress);

        assertThat(findByDistrictOptional).isPresent();
        assertThat(findByDistrictOptional.get()).isEqualTo(savedAddress);

        assertThat(findByCityOptional).isPresent();
        assertThat(findByCityOptional.get()).isEqualTo(savedAddress);

        assertThat(findByPostalCodeOptional).isPresent();
        assertThat(findByPostalCodeOptional.get()).isEqualTo(savedAddress);

        assertThat(findByPhoneOptional).isPresent();
        assertThat(findByPhoneOptional.get()).isEqualTo(savedAddress);
    }

    @Test
    public void update() {
        // Given
        Address newAddress = buildAddress();
        Address savedAddress = addressRepository.save(newAddress);

        // When
        savedAddress.setAddress("Update Test Data");
        savedAddress.setAddress2("Update Test Data");
        savedAddress.setDistrict("Update Test Data");
        savedAddress.setPostalCode("54321");
        savedAddress.setPhone("000-1234");
        addressRepository.save(savedAddress);

        // Then
        Optional<Address> findByIdOptional = addressRepository.findById(savedAddress.getAddressId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedAddress);
    }

    @Test
    public void delete() {
        // Given
        Address newAddress = buildAddress();
        Address savedAddress = addressRepository.save(newAddress);

        // When
        addressRepository.deleteById(savedAddress.getAddressId());

        // Then
        Optional<Address> findByIdOptional = addressRepository.findById(savedAddress.getAddressId());
        assertThat(findByIdOptional).isNotPresent();
    }

    private Address buildAddress() {
        Country newCountry = CountryTest.buildCountry();
        Country savedCountry = countryRepository.save(newCountry);

        City newCity = CityTest.buildCity(savedCountry);
        City savedCity = cityRepository.save(newCity);

        return AddressTest.buildAddress(savedCity);
    }
}