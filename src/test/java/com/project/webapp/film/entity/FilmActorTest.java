package com.project.webapp.film.entity;

public class FilmActorTest {

    public static FilmActor buildFilmActor(Actor actor, Film film) {

        FilmActorId newFilmActorId = FilmActorId.builder()
                .actorId(actor.getActorId())
                .filmId(film.getFilmId())
                .build();

        return FilmActor.builder()
                .id(newFilmActorId)
                .actor(actor)
                .film(film)
                .build();
    }
}