package com.project.webapp.film.repository;

import com.project.webapp.film.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FilmRepositoryTest {

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    FilmRepository filmRepository;

    @Test
    public void create() {
        // Given
        Film newFilm = buildFilm();

        // When
        Film savedFilm = filmRepository.save(newFilm);

        // Then
        Optional<Film> findByIdOptional = filmRepository.findById(newFilm.getFilmId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedFilm);
    }

    @Test
    public void read() {
        // Given
        Film newFilm = buildFilm();
        Film savedFilm = filmRepository.save(newFilm);

        // When
        Optional<Film> findByIdOptional = filmRepository.findById(savedFilm.getFilmId());
        Optional<Film> findByTitleOptional = filmRepository.findByTitle(savedFilm.getTitle());
        Optional<Film> findByDescriptionOptional = filmRepository.findByDescription(savedFilm.getDescription());
        Optional<Film> findByReleaseYearOptional = filmRepository.findByReleaseYear(savedFilm.getReleaseYear());
        Optional<Film> findByLanguageOptional = filmRepository.findByLanguage(savedFilm.getLanguage());
        Optional<Film> findByOriginalLanguageOptional = filmRepository.findByOriginalLanguage(savedFilm.getOriginalLanguage());
        Optional<Film> findByRentalDurationOptional = filmRepository.findByRentalDuration(savedFilm.getRentalDuration());
        Optional<Film> findByRentalRateOptional = filmRepository.findByRentalRate(savedFilm.getRentalRate());
        Optional<Film> findByLengthOptional = filmRepository.findByLength(savedFilm.getLength());
        Optional<Film> findByReplacementCostOptional = filmRepository.findByReplacementCost(savedFilm.getReplacementCost());
        Optional<Film> findByRatingOptional = filmRepository.findByRating(savedFilm.getRating());

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedFilm);

        assertThat(findByTitleOptional).isPresent();
        assertThat(findByTitleOptional.get()).isEqualTo(savedFilm);

        assertThat(findByDescriptionOptional).isPresent();
        assertThat(findByDescriptionOptional.get()).isEqualTo(savedFilm);

        assertThat(findByReleaseYearOptional).isPresent();
        assertThat(findByReleaseYearOptional.get()).isEqualTo(savedFilm);

        assertThat(findByLanguageOptional).isPresent();
        assertThat(findByLanguageOptional.get()).isEqualTo(savedFilm);

        assertThat(findByOriginalLanguageOptional).isPresent();
        assertThat(findByOriginalLanguageOptional.get()).isEqualTo(savedFilm);

        assertThat(findByRentalDurationOptional).isPresent();
        assertThat(findByRentalDurationOptional.get()).isEqualTo(savedFilm);

        assertThat(findByRentalRateOptional).isPresent();
        assertThat(findByRentalRateOptional.get()).isEqualTo(savedFilm);

        assertThat(findByLengthOptional).isPresent();
        assertThat(findByLengthOptional.get()).isEqualTo(savedFilm);

        assertThat(findByReplacementCostOptional).isPresent();
        assertThat(findByReplacementCostOptional.get()).isEqualTo(savedFilm);

        assertThat(findByRatingOptional).isPresent();
        assertThat(findByRatingOptional.get()).isEqualTo(savedFilm);
    }

    @Test
    public void update() {
        // Given
        Film newFilm = buildFilm();
        Film savedFilm = filmRepository.save(newFilm);

        // When
        savedFilm.setTitle("ADAPTATION HOLES");
        savedFilm.setDescription("A Astounding Reflection of a Lumberjack And a Car who must Sink a Lumberjack in A Baloon Factory");
        savedFilm.setReleaseYear(2022);
        savedFilm.setRentalDuration(7);
        savedFilm.setRentalRate(new BigDecimal("2.99"));
        savedFilm.setLength(50);
        savedFilm.setReplacementCost(new BigDecimal("18.99"));
        savedFilm.setRating(Rating.NC_17);
        filmRepository.save(savedFilm);

        // Then
        Optional<Film> findByIdOptional = filmRepository.findById(savedFilm.getFilmId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedFilm);
    }

    @Test
    public void delete() {
        // Given
        Film newFilm = buildFilm();
        Film savedFilm = filmRepository.save(newFilm);

        // When
        filmRepository.delete(savedFilm);

        // Then
        Optional<Film> findByIdOptional = filmRepository.findById(savedFilm.getFilmId());
        assertThat(findByIdOptional).isNotPresent();
    }

    private Film buildFilm() {
        Language newLanguage = LanguageTest.buildLanguage();
        Language savedLanguage = languageRepository.save(newLanguage);

        return FilmTest.buildFilm(savedLanguage);
    }
}