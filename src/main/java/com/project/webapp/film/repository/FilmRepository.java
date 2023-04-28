package com.project.webapp.film.repository;

import com.project.webapp.film.entity.Film;
import com.project.webapp.film.entity.Language;
import com.project.webapp.film.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;


@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

    Optional<Film> findByTitle(String title);

    Optional<Film> findByDescription(String description);

    Optional<Film> findByReleaseYear(Integer releaseYear);

    Optional<Film> findByLanguage(Language language);

    Optional<Film> findByOriginalLanguage(Language originalLanguage);

    Optional<Film> findByRentalDuration(Integer rentalDuration);

    Optional<Film> findByRentalRate(BigDecimal rentalRate);

    Optional<Film> findByLength(Integer length);

    Optional<Film> findByReplacementCost(BigDecimal replacementCost);

    Optional<Film> findByRating(Rating rating);

    // Optional<Film> findBySpecialFeatures(Set<SpecialFeature> );
}