package com.project.webapp.area.controller;


import com.project.webapp.area.dto.CountryIdNameDTO;
import com.project.webapp.area.dto.CountryNameDTO;
import com.project.webapp.area.service.CountryService;
import com.project.webapp.config.dto.ResponseDTO;
import com.project.webapp.config.dto.ResponseSuccessDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping("/country")
    public ResponseEntity<ResponseDTO<CountryIdNameDTO>> createCountry(
            @Valid @RequestBody CountryNameDTO countryDTO
    ) {
        CountryIdNameDTO dto = countryService.create(countryDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDTO<>(HttpStatus.CREATED.value(), dto));
    }

    @GetMapping("/countries")
    public ResponseEntity<ResponseDTO<List<CountryIdNameDTO>>> readCountriesAll() {

        List<CountryIdNameDTO> dtoList = countryService.readAll();

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dtoList));
    }

    @PutMapping("/country/{id}")
    public ResponseEntity<ResponseDTO<CountryIdNameDTO>> updateCountry(
            @PathVariable @NotBlank Integer id,
            @Valid @RequestBody CountryNameDTO countryDTO
    ) {

        CountryIdNameDTO dto = countryService.update(id, countryDTO);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dto));
    }

    @DeleteMapping("/country/{id}")
    public ResponseEntity<ResponseDTO<Integer>> deleteCountry(
            @PathVariable @NotBlank Integer id
    ) {
        countryService.delete(id);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.NO_CONTENT.value()));
    }
}