package com.project.webapp.user.repository;

import com.project.webapp.area.entity.*;
import com.project.webapp.area.repository.AddressRepository;
import com.project.webapp.area.repository.CityRepository;
import com.project.webapp.area.repository.CountryRepository;
import com.project.webapp.store.entity.Store;
import com.project.webapp.store.repository.StoreRepository;
import com.project.webapp.store.entity.StoreTest;
import com.project.webapp.user.entity.Customer;
import com.project.webapp.user.entity.CustomerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void create() {
        // Given
        Customer newCustomer = buildCustomer();

        // When
        Customer savedCustomer = customerRepository.save(newCustomer);

        // Then
        Optional<Customer> findByIdOptional = customerRepository.findById(newCustomer.getCustomerId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedCustomer);
    }

    @Test
    public void read() {
        // Given
        Customer newCustomer = buildCustomer();
        Customer savedCustomer = customerRepository.save(newCustomer);

        // When
        Optional<Customer> findByIdOptional = customerRepository.findById(newCustomer.getCustomerId());

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedCustomer);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
        // Given
        Customer newCustomer = buildCustomer();
        Customer savedCustomer = customerRepository.save(newCustomer);

        // When
        customerRepository.delete(savedCustomer);

        // Then
        Optional<Customer> findByIdOptional = customerRepository.findById(newCustomer.getCustomerId());
        assertThat(findByIdOptional).isNotPresent();
    }

    private Customer buildCustomer() {
        Country newCountry = CountryTest.buildCountry();
        Country savedCountry = countryRepository.save(newCountry);

        City newCity = CityTest.buildCity(savedCountry);
        City savedCity = cityRepository.save(newCity);

        Address newAddress = AddressTest.buildAddress(savedCity);
        Address savedAddress = addressRepository.save(newAddress);

        Store newStore = StoreTest.buildStore(savedAddress);
        Store savedStore = storeRepository.save(newStore);

        return CustomerTest.buildCustomer(savedAddress, savedStore);
    }
}