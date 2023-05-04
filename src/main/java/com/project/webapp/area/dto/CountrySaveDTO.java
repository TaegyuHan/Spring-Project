package com.project.webapp.area.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;


@Getter
@Setter
public class CountrySaveDTO extends CountryDTO {
    @Size(max = 50)
    @NotBlank(message = "country name cannot be blank")
    private String country;
}