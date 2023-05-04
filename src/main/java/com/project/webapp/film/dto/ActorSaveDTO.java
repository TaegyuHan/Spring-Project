package com.project.webapp.film.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActorSaveDTO extends ActorDTO {
    @Size(max = 45)
    @NotBlank(message = "firstName cannot be blank")
    private String firstName;

    @Size(max = 45)
    @NotBlank(message = "lastName cannot be blank")
    private String lastName;
}