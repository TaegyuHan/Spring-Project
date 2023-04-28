package com.project.webapp.film.entity;

public class FilmCategoryTest {

    public static FilmCategory buildFilmCategory(Film film, Category category) {

        FilmCategoryId newFilmCategoryId = FilmCategoryId.builder()
                .filmId(film.getFilmId())
                .categoryId(category.getCategoryId())
                .build();

        return FilmCategory.builder()
                .id(newFilmCategoryId)
                .film(film)
                .category(category)
                .build();
    }
}