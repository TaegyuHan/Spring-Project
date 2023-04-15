package com.project.webapp.film.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "film",
        indexes = {
                @Index(name = "idx_title", columnList = "title"),
                @Index(name = "idx_fk_language_id", columnList = "language_id"),
                @Index(name = "idx_fk_original_language_id", columnList = "original_language_id")
        })
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id", columnDefinition = "SMALLINT UNSIGNED")
    private Integer filmId;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "release_year", columnDefinition = "YEAR")
    private Integer releaseYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_film_language"))
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_language_id",
            foreignKey = @ForeignKey(name = "fk_film_language_original"))
    private Language originalLanguage;

    @Column(name = "rental_duration", nullable = false,
            columnDefinition = "SMALLINT UNSIGNED DEFAULT 3")
    private Integer rentalDuration;

    @Column(name = "rental_rate", nullable = false,
            precision = 4, scale = 2, columnDefinition = "DECIMAL(4,2) DEFAULT 4.99")
    private BigDecimal rentalRate;

    @Column(name = "length", columnDefinition = "SMALLINT UNSIGNED")
    private Integer length;

    @Column(name = "replacement_cost", nullable = false,
            precision = 5, scale = 2, columnDefinition = "DECIMAL(5,2) DEFAULT 19.99")
    private BigDecimal replacementCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating", columnDefinition = "ENUM('G','PG','PG_13','R','NC_17') DEFAULT 'G'")
    private Rating rating;

    @ElementCollection
    @CollectionTable(name = "film_special_features", joinColumns = @JoinColumn(name = "film_id"))
    @Enumerated(EnumType.STRING)
    private Set<SpecialFeature> specialFeatures;

    @UpdateTimestamp
    @Column(name = "last_update", nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp lastUpdate;
}