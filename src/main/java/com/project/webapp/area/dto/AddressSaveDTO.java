package com.project.webapp.area.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressSaveDTO extends AddressDTO {
    @Size(max = 50)
    @NotBlank(message = "address cannot be blank")
    private String address;

    private String address2;

    @Size(max = 50)
    @NotBlank(message = "district cannot be blank")
    private String district;

    @NotNull(message = "cityId cannot be null")
    private Integer cityId;

    @Size(max = 10)
    private String postalCode;

    @Size(max = 20)
    @NotBlank(message = "phone cannot be blank")
    private String phone;
}