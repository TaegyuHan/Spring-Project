package com.project.webapp.film.controller;

import com.project.webapp.config.ApiVersionController;
import com.project.webapp.config.dto.ResponseDTO;
import com.project.webapp.config.dto.ResponseSuccessDTO;
import com.project.webapp.film.dto.FilmSaveDTO;
import com.project.webapp.film.dto.FilmSearchDTO;
import com.project.webapp.film.service.FilmService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ApiVersionController.URL_API_VERSION)
public class FilmController {

    @Autowired
    private FilmService filmService;

    @PostMapping("/film")
    public ResponseEntity<ResponseDTO<FilmSearchDTO>> createFilm(
            @Valid @RequestBody FilmSaveDTO FilmSaveDTO
    ) {
        FilmSearchDTO dto = filmService.create(FilmSaveDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDTO<>(HttpStatus.CREATED.value(), dto));
    }

    @GetMapping("/film/{id}")
    public ResponseEntity<ResponseDTO<FilmSearchDTO>> readFilm(
            @PathVariable @NotBlank Integer id
    ) {
        FilmSearchDTO dto = filmService.findById(id);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dto));
   }

    @PutMapping("/film/{id}")
    public ResponseEntity<ResponseDTO<FilmSearchDTO>> updateFilm(
            @PathVariable @NotBlank Integer id,
            @Valid @RequestBody FilmSaveDTO FilmSaveDTO
    ) {
        FilmSearchDTO dto = filmService.update(id, FilmSaveDTO);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dto));
    }

    @DeleteMapping("/film/{id}")
    public ResponseEntity<ResponseDTO<Integer>> deleteFilm(
            @PathVariable @NotBlank Integer id
    ) {
        filmService.delete(id);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.NO_CONTENT.value()));
    }
}