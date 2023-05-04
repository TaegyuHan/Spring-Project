package com.project.webapp.area.controller;


import com.project.webapp.area.dto.CountrySearchDTO;
import com.project.webapp.area.dto.CountrySaveDTO;
import com.project.webapp.area.service.CountryService;
import com.project.webapp.config.ApiVersionController;
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
@RequestMapping(ApiVersionController.URL_API_VERSION)
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping("/country")
    public ResponseEntity<ResponseDTO<CountrySearchDTO>> createCountry(
            @Valid @RequestBody CountrySaveDTO countryDTO
    ) {
        CountrySearchDTO dto = countryService.create(countryDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDTO<>(HttpStatus.CREATED.value(), dto));
    }

    @GetMapping("/countries")
    public ResponseEntity<ResponseDTO<List<CountrySearchDTO>>> readCountriesAll() {

        List<CountrySearchDTO> dtoList = countryService.readAll();

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dtoList));
    }

    @PutMapping("/country/{id}")
    public ResponseEntity<ResponseDTO<CountrySearchDTO>> updateCountry(
            @PathVariable @NotBlank Integer id,
            @Valid @RequestBody CountrySaveDTO countryDTO
    ) {

        CountrySearchDTO dto = countryService.update(id, countryDTO);

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