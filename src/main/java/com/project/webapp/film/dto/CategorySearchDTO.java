package com.project.webapp.film.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CategorySearchDTO extends CategorySaveDTO {
    @NotNull(message = "categoryId cannot be null")
    private Integer categoryId;

    @NotBlank(message = "lastUpdate cannot be not")
    private Timestamp lastUpdate;
}