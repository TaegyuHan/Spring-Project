package com.project.webapp.area.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AddressSearchDTO extends AddressSaveDTO {
    @NotNull(message = "addressId cannot be null")
    private Integer addressId;

    @NotBlank(message = "lastUpdate cannot be blank")
    private Timestamp lastUpdate;
}