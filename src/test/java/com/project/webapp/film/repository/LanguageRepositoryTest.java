package com.project.webapp.film.repository;


import com.project.webapp.film.entity.Language;
import com.project.webapp.film.entity.LanguageTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LanguageRepositoryTest {

    @Autowired
    LanguageRepository languageRepository;

    @Test
    public void create() {
        // Given
        Language newLanguage = LanguageTest.buildLanguage();

        // When
        Language savedLanguage = languageRepository.save(newLanguage);

        // Then
        Optional<Language> findByIdOptional = languageRepository.findById(newLanguage.getLanguageId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedLanguage);
    }

    @Test
    public void read() {
        // Given
        Language newLanguage = LanguageTest.buildLanguage();
        Language savedLanguage = languageRepository.save(newLanguage);

        // When
        Optional<Language> findByIdOptional = languageRepository.findById(newLanguage.getLanguageId());
        Optional<Language> findByNameOptional = languageRepository.findByName(newLanguage.getName());

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedLanguage);

        assertThat(findByNameOptional).isPresent();
        assertThat(findByNameOptional.get()).isEqualTo(savedLanguage);
    }

    @Test
    public void update() {
        // Given
        Language newLanguage = LanguageTest.buildLanguage();
        Language savedLanguage = languageRepository.save(newLanguage);

        // When
        savedLanguage.setName("Japanese");
        languageRepository.save(savedLanguage);

        // Then
        Optional<Language> findByIdOptional = languageRepository.findById(savedLanguage.getLanguageId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedLanguage);
    }

    @Test
    public void delete() {
        // Given
        Language newLanguage = LanguageTest.buildLanguage();
        Language savedLanguage = languageRepository.save(newLanguage);

        // When
        languageRepository.delete(savedLanguage);

        // Then
        Optional<Language> findByIdOptional = languageRepository.findById(savedLanguage.getLanguageId());
        assertThat(findByIdOptional).isNotPresent();
    }
}