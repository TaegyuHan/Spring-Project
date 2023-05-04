package com.project.webapp.film.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategorySaveDTO extends CategoryDTO {
    @Size(max = 25)
    @NotBlank(message = "category cannot be blank")
    private String name;
}