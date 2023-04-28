package com.project.webapp.film.repository;

import com.project.webapp.film.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FilmTextRepositoryTest {

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    FilmTextRepository filmTextRepository;

    @Test
    public void create() {
        // Given
        FilmText newFilmText = buildFilmText();

        // When
        FilmText savedFilmText = filmTextRepository.save(newFilmText);

        // Then
        Optional<FilmText> findByIdOptional = filmTextRepository.findByFilmId(newFilmText.getFilmId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedFilmText);
    }

    @Test
    public void read() {
        // Given
        FilmText newFilmText = buildFilmText();
        FilmText savedFilmText = filmTextRepository.save(newFilmText);

        // When
        Optional<FilmText> findByIdOptional = filmTextRepository.findByFilmId(newFilmText.getFilmId());
        Optional<FilmText> findByTitleOptional = filmTextRepository.findByTitle(newFilmText.getTitle());
        Optional<FilmText> findByDescriptionOptional = filmTextRepository.findByDescription(newFilmText.getDescription());

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedFilmText);

        assertThat(findByTitleOptional).isPresent();
        assertThat(findByTitleOptional.get()).isEqualTo(savedFilmText);

        assertThat(findByDescriptionOptional).isPresent();
        assertThat(findByDescriptionOptional.get()).isEqualTo(savedFilmText);
    }

    @Test
    public void update() {
        // Given
        FilmText newFilmText = buildFilmText();
        FilmText savedFilmText = filmTextRepository.save(newFilmText);

        // When
        String updatedTitle = "Updated Title";
        String updatedDescription = "Updated Description";
        savedFilmText.setTitle(updatedTitle);
        savedFilmText.setDescription(updatedDescription);
        FilmText updatedFilmText = filmTextRepository.save(savedFilmText);

        // Then
        Optional<FilmText> findByIdOptional = filmTextRepository.findByFilmId(newFilmText.getFilmId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(updatedFilmText);
    }

    @Test
    public void delete() {
        // Given
        FilmText newFilmText = buildFilmText();
        FilmText savedFilmText = filmTextRepository.save(newFilmText);

        // When
        filmTextRepository.delete(savedFilmText);

        // Then
        Optional<FilmText> findByIdOptional = filmTextRepository.findByFilmId(savedFilmText.getFilmId());
        assertThat(findByIdOptional).isNotPresent();
    }

    private FilmText buildFilmText() {
        Language newLanguage = LanguageTest.buildLanguage();
        Language savedLanguage = languageRepository.save(newLanguage);

        Film newFilm = FilmTest.buildFilm(savedLanguage);
        Film savedFilm = filmRepository.save(newFilm);

        return FilmTextTest.buildFilmText(savedFilm);
    }
}