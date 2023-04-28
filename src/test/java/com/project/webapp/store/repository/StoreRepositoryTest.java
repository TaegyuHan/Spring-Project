package com.project.webapp.store.repository;

import com.project.webapp.area.entity.*;
import com.project.webapp.area.repository.AddressRepository;
import com.project.webapp.area.repository.CityRepository;
import com.project.webapp.area.repository.CountryRepository;
import com.project.webapp.area.repository.repository.StoreRepository;
import com.project.webapp.store.entity.Store;
import com.project.webapp.store.entity.StoreTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void create() {
        // Given
        Store newStore = buildStore();

        // When
        Store savedStore = storeRepository.save(newStore);

        // Then
        Optional<Store> findByIdOptional = storeRepository.findById(newStore.getStoreId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedStore);
    }

    @Test
    public void read() {
        // Given
        Store newStore = buildStore();
        Store savedStore = storeRepository.save(newStore);

        // When
        Optional<Store> findByIdOptional = storeRepository.findById(savedStore.getStoreId());

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedStore);
    }

    @Test
    public void update() {
        // 가게는 수정 못함
    }

    @Test
    public void delete() {
        // Given
        Store newStore = buildStore();
        Store savedStore = storeRepository.save(newStore);

        // When
        storeRepository.delete(newStore);

        // Then
        Optional<Store> findByIdOptional = storeRepository.findById(savedStore.getStoreId());
        assertThat(findByIdOptional).isNotPresent();
    }

    private Store buildStore() {
        Country newCountry = CountryTest.buildCountry();
        Country savedCountry = countryRepository.save(newCountry);

        City newCity = CityTest.buildCity(savedCountry);
        City savedCity = cityRepository.save(newCity);

        Address newAddress = AddressTest.buildAddress(savedCity);
        Address savedAddress = addressRepository.save(newAddress);

        return StoreTest.buildStore(savedAddress);
    }
}