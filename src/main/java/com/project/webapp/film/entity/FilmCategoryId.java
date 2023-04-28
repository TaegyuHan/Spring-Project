package com.project.webapp.film.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class FilmCategoryId implements Serializable {

    @Column(name = "film_id", nullable = false,
            columnDefinition = "SMALLINT UNSIGNED")
    private Integer filmId;

    @Column(name = "category_id", nullable = false,
            columnDefinition = "TINYINT UNSIGNED")
    private Integer categoryId;
}