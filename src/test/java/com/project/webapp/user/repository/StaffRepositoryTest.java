package com.project.webapp.user.repository;

import com.project.webapp.area.entity.*;
import com.project.webapp.area.repository.AddressRepository;
import com.project.webapp.area.repository.CityRepository;
import com.project.webapp.area.repository.CountryRepository;
import com.project.webapp.store.entity.Store;
import com.project.webapp.store.repository.StoreRepository;
import com.project.webapp.store.entity.StoreTest;
import com.project.webapp.user.entity.Staff;
import com.project.webapp.user.entity.StaffTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StaffRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    public void create() {
        // Given
        Staff newStaff = buildStaff();

        // When
        Staff savedStaff = staffRepository.save(newStaff);

        // Then
        Optional<Staff> findByIdOptional = staffRepository.findById(newStaff.getStaffId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedStaff);
    }

    @Test
    public void read() {
        // Given
        Staff newStaff = buildStaff();
        Staff savedStaff = staffRepository.save(newStaff);

        // When
        Optional<Staff> findByIdOptional = staffRepository.findById(newStaff.getStaffId());

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedStaff);
    }

    @Test
    public void update() {
        // Given
        Staff newStaff = buildStaff();
        Staff savedStaff = staffRepository.save(newStaff);

        // When
        savedStaff.setFirstName("Tae Gyu");
        savedStaff.setLastName("Han");
        staffRepository.save(savedStaff);

        // Then
        Optional<Staff> findByIdOptional = staffRepository.findById(savedStaff.getStaffId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedStaff);
    }

    @Test
    public void delete() {
        // Given
        Staff newStaff = buildStaff();
        Staff savedStaff = staffRepository.save(newStaff);

        // when
        staffRepository.delete(savedStaff);

        // Then
        Optional<Staff> findByIdOptional = staffRepository.findById(savedStaff.getStaffId());
        assertThat(findByIdOptional).isNotPresent();
    }

    private Staff buildStaff() {
        Country newCountry = CountryTest.buildCountry();
        Country savedCountry = countryRepository.save(newCountry);

        City newCity = CityTest.buildCity(savedCountry);
        City savedCity = cityRepository.save(newCity);

        Address newAddress = AddressTest.buildAddress(savedCity);
        Address savedAddress = addressRepository.save(newAddress);

        Store newStore = StoreTest.buildStore(savedAddress);
        Store savedStore = storeRepository.save(newStore);

        return StaffTest.buildStaff(savedAddress, savedStore);
    }
}