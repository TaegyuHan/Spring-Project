package com.project.webapp.film.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class FilmActorId implements Serializable {

    @Column(name = "actor_id", nullable = false,
            columnDefinition = "SMALLINT UNSIGNED")
    private Integer actorId;

    @Column(name = "film_id", nullable = false,
            columnDefinition = "SMALLINT UNSIGNED")
    private Integer filmId;
}