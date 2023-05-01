package com.project.webapp.area.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CityIdNameDTO extends CityDTO {
    @NotNull
    private Integer cityId;

    @Size(max = 50)
    @NotBlank(message = "city name cannot be blank")
    private String city;
}