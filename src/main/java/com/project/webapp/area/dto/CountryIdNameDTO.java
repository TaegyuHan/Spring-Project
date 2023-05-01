package com.project.webapp.area.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;


@Getter
@Setter
public class CountryIdNameDTO extends CountryNameDTO {
    @NotNull
    private Integer countryId;
}