package com.project.webapp.area.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AddressSearchDTO extends AddressSaveDTO {
    @NotNull(message = "addressId cannot be null")
    private Integer addressId;

    @NotNull(message = "lastUpdate cannot be null")
    private Timestamp lastUpdate;
}