package com.project.webapp.film.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class FilmSearchDTO extends FilmSaveDTO {
    @NotNull(message = "actorId cannot be null")
    private Integer filmId;

    @NotBlank(message = "lastUpdate cannot be not")
    private Timestamp lastUpdate;
}
