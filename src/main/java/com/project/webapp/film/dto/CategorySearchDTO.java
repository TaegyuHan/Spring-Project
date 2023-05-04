package com.project.webapp.film.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySearchDTO extends CategorySaveDTO {
    @NotNull(message = "categoryId cannot be null")
    private Integer categoryId;
}