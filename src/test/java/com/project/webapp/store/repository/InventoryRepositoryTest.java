package com.project.webapp.store.repository;

import com.project.webapp.area.entity.*;
import com.project.webapp.area.repository.AddressRepository;
import com.project.webapp.area.repository.CityRepository;
import com.project.webapp.area.repository.CountryRepository;
import com.project.webapp.film.entity.*;
import com.project.webapp.film.repository.FilmRepository;
import com.project.webapp.film.repository.LanguageRepository;
import com.project.webapp.store.entity.Inventory;
import com.project.webapp.store.entity.InventoryTest;
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
class InventoryRepositoryTest {

    @Autowired
    LanguageRepository languageRepository;
    @Autowired
    FilmRepository filmRepository;
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

    @Test
    public void create() {
        // Given
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

        Inventory newInventory = InventoryTest.buildInventory(savedFilm, savedStore);

        // When
        Inventory savedInventory = inventoryRepository.save(newInventory);

        // Then
        Optional<Inventory> findByIdOptional = inventoryRepository.findById(newInventory.getInventoryId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedInventory);
    }

    @Test
    public void read() {
        // Given
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

        Inventory newInventory = InventoryTest.buildInventory(savedFilm, savedStore);
        Inventory savedInventory = inventoryRepository.save(newInventory);

        // When
        Optional<Inventory> findByIdOptional = inventoryRepository.findById(newInventory.getInventoryId());

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedInventory);
    }

    @Test
    public void update() {
        // 수정 안함
    }

    @Test
    public void delete() {
        // Given
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

        Inventory newInventory = InventoryTest.buildInventory(savedFilm, savedStore);
        Inventory savedInventory = inventoryRepository.save(newInventory);

        // When
        inventoryRepository.delete(savedInventory);

        // Then
        Optional<Inventory> findByIdOptional = inventoryRepository.findById(savedInventory.getInventoryId());
        assertThat(findByIdOptional).isNotPresent();
    }
}