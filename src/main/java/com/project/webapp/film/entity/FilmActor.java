package com.project.webapp.film.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "film_actor",
        indexes = {
                @Index(name = "idx_fk_film_id", columnList = "film_id")
        }
)
public class FilmActor {

    @EmbeddedId
    private FilmActorId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", nullable = false,
            insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_film_actor_actor"))
    private Actor actor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false,
            insertable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_film_actor_film"))
    private Film film;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp lastUpdate;
}