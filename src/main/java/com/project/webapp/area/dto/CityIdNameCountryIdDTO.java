package com.project.webapp.area.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityIdNameCountryIdDTO extends CityNameCountryIdDTO {
    @NotNull
    private Integer cityId;
}