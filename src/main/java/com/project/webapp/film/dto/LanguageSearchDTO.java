package com.project.webapp.film.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class LanguageSearchDTO extends LanguageSaveDTO {
    @NotNull(message = "actorId cannot be null")
    private Integer languageId;

    @NotBlank(message = "lastUpdate cannot be not")
    private Timestamp lastUpdate;
}
