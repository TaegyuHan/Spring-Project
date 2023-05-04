package com.project.webapp.film.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LanguageSaveDTO extends LanguageDTO {
    @Size(max = 20)
    @NotBlank(message = "name cannot be blank")
    private String name;
}
