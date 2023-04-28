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
class FilmActorRepositoryTest {

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    FilmActorRepository filmActorRepository;

    @Test
    public void create() {
        // Given
        FilmActor newFilmActor = buildFilmActor();

        // When
        FilmActor savedFilmActor = filmActorRepository.save(newFilmActor);

        // Then
        Optional<FilmActor> findByIdOptional = filmActorRepository.findById(newFilmActor.getId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedFilmActor);
    }

    @Test
    public void read() {
        // Given
        FilmActor newFilmActor = buildFilmActor();
        FilmActor savedFilmActor = filmActorRepository.save(newFilmActor);

        // When
        Optional<FilmActor> findByIdOptional = filmActorRepository.findById(savedFilmActor.getId());

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedFilmActor);
    }

    @Test
    public void update() {
        // 삭제후 다시 생성 선호
    }

    @Test
    public void delete() {
        // Given
        FilmActor newFilmActor = buildFilmActor();
        FilmActor savedFilmActor = filmActorRepository.save(newFilmActor);

        // When
        filmActorRepository.delete(savedFilmActor);

        // Then
        Optional<FilmActor> findByIdOptional = filmActorRepository.findById(savedFilmActor.getId());
        assertThat(findByIdOptional).isNotPresent();
    }

    private FilmActor buildFilmActor() {
        Language newLanguage = LanguageTest.buildLanguage();
        Language savedLanguage = languageRepository.save(newLanguage);

        Film newFilm = FilmTest.buildFilm(savedLanguage);
        Film savedFilm = filmRepository.save(newFilm);

        Actor newActor = ActorTest.buildActor();
        Actor savedActor = actorRepository.save(newActor);

        return FilmActorTest.buildFilmActor(savedActor, savedFilm);
    }
}