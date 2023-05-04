package com.project.webapp.film.controller;


import com.project.webapp.config.ApiVersionController;
import com.project.webapp.config.dto.ResponseDTO;
import com.project.webapp.config.dto.ResponseSuccessDTO;
import com.project.webapp.film.dto.LanguageSaveDTO;
import com.project.webapp.film.dto.LanguageSearchDTO;
import com.project.webapp.film.service.LanguageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ApiVersionController.URL_API_VERSION)
public class LanguageController {
    @Autowired
    private LanguageService languageService;

    @PostMapping("/language")
    public ResponseEntity<ResponseDTO<LanguageSearchDTO>> createLanguage(
            @Valid @RequestBody LanguageSaveDTO LanguageSaveDTO
    ) {
        LanguageSearchDTO dto = languageService.create(LanguageSaveDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDTO<>(HttpStatus.CREATED.value(), dto));
    }

    @GetMapping("/language/{id}")
    public ResponseEntity<ResponseDTO<LanguageSearchDTO>> readLanguage(
            @PathVariable @NotBlank Integer id
    ) {
        LanguageSearchDTO dto = languageService.findByid(id);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dto));
    }

    @PutMapping("/language/{id}")
    public ResponseEntity<ResponseDTO<LanguageSearchDTO>> updateLanguage(
            @PathVariable @NotBlank Integer id,
            @Valid @RequestBody LanguageSaveDTO LanguageSaveDTO
    ) {
        LanguageSearchDTO dto = languageService.update(id, LanguageSaveDTO);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dto));
    }

    @DeleteMapping("/language/{id}")
    public ResponseEntity<ResponseDTO<Integer>> deleteLanguage(
            @PathVariable @NotBlank Integer id
    ) {
        languageService.delete(id);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.NO_CONTENT.value()));
    }
}