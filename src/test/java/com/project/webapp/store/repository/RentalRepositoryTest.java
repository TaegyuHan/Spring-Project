package com.project.webapp.store.repository;

import com.project.webapp.area.entity.*;
import com.project.webapp.area.repository.AddressRepository;
import com.project.webapp.area.repository.CityRepository;
import com.project.webapp.area.repository.CountryRepository;
import com.project.webapp.film.entity.*;
import com.project.webapp.film.repository.FilmRepository;
import com.project.webapp.film.repository.LanguageRepository;
import com.project.webapp.store.entity.*;
import com.project.webapp.user.entity.Customer;
import com.project.webapp.user.entity.CustomerTest;
import com.project.webapp.user.entity.Staff;
import com.project.webapp.user.entity.StaffTest;
import com.project.webapp.user.repository.CustomerRepository;
import com.project.webapp.user.repository.StaffRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RentalRepositoryTest {

    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void create() {
        // Given
        Rental newRental = buildRental();

        // When
        Rental savedRental = rentalRepository.save(newRental);

        // Then
        Optional<Rental> findByIdOptional = rentalRepository.findById(newRental.getRentalId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedRental);
    }

    @Test
    public void read() {
        // Given
        Rental newRental = buildRental();
        Rental savedRental = rentalRepository.save(newRental);

        // When
        Optional<Rental> findByIdOptional = rentalRepository.findById(newRental.getRentalId());

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedRental);
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
        // Given
        Rental newRental = buildRental();
        Rental savedRental = rentalRepository.save(newRental);

        // when
        rentalRepository.delete(savedRental);

        // Then
        Optional<Rental> findByIdOptional = rentalRepository.findById(savedRental.getRentalId());
        assertThat(findByIdOptional).isNotPresent();
    }

    private Rental buildRental() {
        Language newLanguage = LanguageTest.buildLanguage();
        Language savedLanguage = languageRepository.save(newLanguage);

        Film newFilm = FilmTest.buildFilm(savedLanguage);
        Film savedFilm = filmRepository.save(newFilm);

        Country newCountry = CountryTest.buildCountry();
        Country savedCountry = countryRepository.save(newCountry);

        City newCity = CityTest.buildCity(savedCountry);
        City savedCity = cityRepository.save(newCity);

        Address newAddress = AddressTest.buildAddress(savedCity);
        Address savedAddress = addressRepository.save(newAddress);

        Store newStore = StoreTest.buildStore(savedAddress);
        Store savedStore = storeRepository.save(newStore);

        Customer newCustomer = CustomerTest.buildCustomer(savedAddress, savedStore);
        Customer savedCustomer = customerRepository.save(newCustomer);

        Staff newStaff = StaffTest.buildStaff(savedAddress, savedStore);
        Staff savedStaff = staffRepository.save(newStaff);

        Inventory newInventory = InventoryTest.buildInventory(savedFilm, savedStore);
        Inventory savedInventory = inventoryRepository.save(newInventory);

        return RentalTest.buildRental(savedInventory, savedCustomer, savedStaff);
    }
}