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
class FilmCategoryRepositoryTest {

    @Autowired
    private FilmCategoryRepository filmCategoryRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create() {
        // Given
        FilmCategory newFilmCategory = buildFilmCategory();

        // When
        FilmCategory savedFilmCategory = filmCategoryRepository.save(newFilmCategory);

        // Then
        Optional<FilmCategory> findByIdOptional = filmCategoryRepository.findById(newFilmCategory.getId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedFilmCategory);
    }

    @Test
    public void read() {
        // Given
        FilmCategory newFilmCategory = buildFilmCategory();
        FilmCategory savedFilmCategory = filmCategoryRepository.save(newFilmCategory);

        // When
        Optional<FilmCategory> findByIdOptional = filmCategoryRepository.findById(savedFilmCategory.getId());

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedFilmCategory);
    }

    @Test
    public void update() {
        // 삭제후 다시 생성 선호
    }

    @Test
    public void delete() {
        // Given
        FilmCategory newFilmCategory = buildFilmCategory();
        FilmCategory savedFilmCategory = filmCategoryRepository.save(newFilmCategory);

        // When
        filmCategoryRepository.delete(savedFilmCategory);

        // Then
        Optional<FilmCategory> findByIdOptional = filmCategoryRepository.findById(savedFilmCategory.getId());
        assertThat(findByIdOptional).isNotPresent();
    }

    private FilmCategory buildFilmCategory() {
        Language newLanguage = LanguageTest.buildLanguage();
        Language savedLanguage = languageRepository.save(newLanguage);

        Film newFilm = FilmTest.buildFilm(savedLanguage);
        Film savedFilm = filmRepository.save(newFilm);

        Category newCategory = CategoryTest.buildCategory();
        Category savedCategory = categoryRepository.save(newCategory);

        return FilmCategoryTest.buildFilmCategory(savedFilm, savedCategory);
    }
}