package com.project.webapp.film.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilmSaveDTO extends FilmDTO {

    private List<Integer> categoryIds;

    private List<Integer> actorIds;
}