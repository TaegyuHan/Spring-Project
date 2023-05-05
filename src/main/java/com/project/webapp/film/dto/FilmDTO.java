package com.project.webapp.film.dto;

import com.project.webapp.film.entity.Rating;
import com.project.webapp.film.entity.SpecialFeature;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public abstract class FilmDTO {
    @Size(max = 128)
    @NotBlank(message = "title cannot be blank")
    private String title;

    private String description;

    private Integer releaseYear;

    @NotNull(message = "languageId cannot be null")
    private Integer languageId;

    private Integer originalLanguageId;

    @NotNull(message = "rentalDuration cannot be null")
    private Integer rentalDuration;

    @NotNull(message = "rentalRate cannot be null")
    private BigDecimal rentalRate;

    private Integer length;

    @NotNull(message = "replacementCost cannot be null")
    private BigDecimal replacementCost;

    private Rating rating;

    private Set<SpecialFeature> specialFeatures;
}