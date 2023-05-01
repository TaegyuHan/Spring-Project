package com.project.webapp.area.controller;

import com.project.webapp.area.dto.CityIdNameCountryIdDTO;
import com.project.webapp.area.dto.CityIdNameDTO;
import com.project.webapp.area.dto.CityNameCountryIdDTO;
import com.project.webapp.area.service.CityService;
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
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping("/city")
    public ResponseEntity<ResponseDTO<CityIdNameCountryIdDTO>> createCity(
            @Valid @RequestBody CityNameCountryIdDTO cityNameCountryIdDTO
    ) {
        CityIdNameCountryIdDTO dto = cityService.create(cityNameCountryIdDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDTO<>(HttpStatus.CREATED.value(), dto));
    }

    @GetMapping("/cities")
    public ResponseEntity<ResponseDTO<List<CityIdNameDTO>>> readCitiesByCountryId(
            @RequestParam(name = "country_id") @NotBlank Integer countryId
    ) {
        List<CityIdNameDTO> dtoList = cityService.findByCountryId(countryId);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dtoList));
    }

    @PutMapping("/city/{id}")
    public ResponseEntity<ResponseDTO<CityIdNameCountryIdDTO>> updateCitise(
            @PathVariable @NotBlank Integer id,
            @Valid @RequestBody CityNameCountryIdDTO cityIdNameCountryIdDTO
    ) {
        CityIdNameCountryIdDTO dto = cityService.update(id, cityIdNameCountryIdDTO);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dto));
    }

    @DeleteMapping("/city/{id}")
    public ResponseEntity<ResponseDTO<Integer>> deleteCity(
            @PathVariable @NotBlank Integer id
    ) {
        cityService.delete(id);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.NO_CONTENT.value()));
    }
}