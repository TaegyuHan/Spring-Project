package com.project.webapp.film.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "film_category")
public class FilmCategory implements Serializable {

    @EmbeddedId
    private FilmCategoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false,
            insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_film_category_film"))
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false,
            insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_film_category_category"))
    private Category category;

    @Column(name = "last_update", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp lastUpdate;

}