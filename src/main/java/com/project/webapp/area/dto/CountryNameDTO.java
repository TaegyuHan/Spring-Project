package com.project.webapp.area.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;


@Getter
@Setter
public class CountryNameDTO extends CountryDTO {
    @NotBlank(message = "country name cannot be blank")
    @Size(max = 50)
    private String country;
}