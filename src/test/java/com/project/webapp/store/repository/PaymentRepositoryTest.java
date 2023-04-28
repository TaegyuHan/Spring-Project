package com.project.webapp.store.repository;

import com.project.webapp.area.entity.*;
import com.project.webapp.area.repository.AddressRepository;
import com.project.webapp.area.repository.CityRepository;
import com.project.webapp.area.repository.CountryRepository;
import com.project.webapp.area.repository.repository.InventoryRepository;
import com.project.webapp.area.repository.repository.PaymentRepository;
import com.project.webapp.area.repository.repository.RentalRepository;
import com.project.webapp.area.repository.repository.StoreRepository;
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
class PaymentRepositoryTest {

    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private PaymentRepository paymentRepository;
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
    private CustomerRepository customerRepository;

    @Test
    public void create() {
        // Given
        Payment newPayment = buildPayment();

        // When
        Payment savedPayment = paymentRepository.save(newPayment);

        // Then
        Optional<Payment> findByIdOptional = paymentRepository.findById(newPayment.getPaymentId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedPayment);
    }

    @Test
    public void read() {
        // Given
        Payment newPayment = buildPayment();
        Payment savedPayment = paymentRepository.save(newPayment);

        // When
        Optional<Payment> findByIdOptional = paymentRepository.findById(savedPayment.getPaymentId());

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedPayment);
    }

    @Test
    public void update() {
        // 나중에 수정
    }

    @Test
    public void delete() {
        // Given
        Payment newPayment = buildPayment();
        Payment savedPayment = paymentRepository.save(newPayment);

        // When
        paymentRepository.delete(savedPayment);

        // Then
        Optional<Payment> findByIdOptional = paymentRepository.findById(savedPayment.getPaymentId());
        assertThat(findByIdOptional).isNotPresent();
    }

    private Payment buildPayment() {
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

        Language newLanguage = LanguageTest.buildLanguage();
        Language savedLanguage = languageRepository.save(newLanguage);

        Film newFilm = FilmTest.buildFilm(savedLanguage);
        Film savedFilm = filmRepository.save(newFilm);

        Inventory newInventory = InventoryTest.buildInventory(savedFilm, savedStore);
        Inventory savedInventory = inventoryRepository.save(newInventory);

        Rental newRental = RentalTest.buildRental(savedInventory, savedCustomer, savedStaff);
        Rental savedRental = rentalRepository.save(newRental);

        return PaymentTest.buildPayment(savedCustomer, savedStaff, savedRental);
    }
}