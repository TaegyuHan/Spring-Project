package com.project.webapp.film.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class FilmTest {

    public static Film buildFilm(Language language) {
        Set<SpecialFeature> specialFeatures = new HashSet<>();
        specialFeatures.add(SpecialFeature.TRAILERS);
        specialFeatures.add(SpecialFeature.DELETED_SCENES);

        return Film.builder()
                .title("ACE GOLDFINGER")
                .description("A Astounding Epistle of a Database Administrator And a Explorer who must Find a Car in Ancient China")
                .releaseYear(2006)
                .language(language)
                .originalLanguage(language)
                .rentalDuration(3)
                .rentalRate(new BigDecimal("4.99"))
                .length(48)
                .replacementCost(new BigDecimal("12.99"))
                .rating(Rating.R)
                .specialFeatures(specialFeatures)
                .build();
    }
}