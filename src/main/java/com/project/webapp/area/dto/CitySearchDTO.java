package com.project.webapp.area.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitySearchDTO extends CitySaveDTO {
    @NotNull
    private Integer cityId;
}