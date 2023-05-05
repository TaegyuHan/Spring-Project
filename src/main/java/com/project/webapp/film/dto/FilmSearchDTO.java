package com.project.webapp.film.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class FilmSearchDTO extends FilmDTO {
    @NotNull(message = "actorId cannot be null")
    private Integer filmId;

    private List<CategorySearchDTO> categorySearchDTOs;

    private List<ActorSearchDTO> actorSearchDTOs;

    @NotBlank(message = "lastUpdate cannot be not")
    private Timestamp lastUpdate;
}
