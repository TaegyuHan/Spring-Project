package com.project.webapp.film.entity;

public class FilmTextTest {

    public static FilmText buildFilmText(Film film) {

        return FilmText.builder()
                .filmId(film.getFilmId())
                .title(film.getTitle())
                .description(film.getDescription())
                .build();
    }
}