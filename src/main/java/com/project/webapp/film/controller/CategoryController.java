package com.project.webapp.film.controller;

import com.project.webapp.config.ApiVersionController;
import com.project.webapp.config.dto.ResponseDTO;
import com.project.webapp.config.dto.ResponseSuccessDTO;
import com.project.webapp.film.dto.CategorySaveDTO;
import com.project.webapp.film.dto.CategorySearchDTO;
import com.project.webapp.film.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ApiVersionController.URL_API_VERSION)
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<ResponseDTO<CategorySearchDTO>> createCountry(
            @Valid @RequestBody CategorySaveDTO CategorySaveDTO
    ) {
        CategorySearchDTO dto = categoryService.create(CategorySaveDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDTO<>(HttpStatus.CREATED.value(), dto));
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ResponseDTO<CategorySearchDTO>> readCountriesAll(
            @PathVariable @NotBlank Integer id
    ) {
        CategorySearchDTO dto = categoryService.findByid(id);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dto));
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<ResponseDTO<CategorySearchDTO>> updateCountry(
            @PathVariable @NotBlank Integer id,
            @Valid @RequestBody CategorySaveDTO CategorySaveDTO
    ) {
        CategorySearchDTO dto = categoryService.update(id, CategorySaveDTO);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dto));
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<ResponseDTO<Integer>> deleteCountry(
            @PathVariable @NotBlank Integer id
    ) {
        categoryService.delete(id);
        
        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.NO_CONTENT.value()));
    }
}