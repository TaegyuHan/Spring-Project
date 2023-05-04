package com.project.webapp.area.controller;

import com.project.webapp.area.dto.CitySearchDTO;
import com.project.webapp.area.dto.CitySearchByCountryIdDTO;
import com.project.webapp.area.dto.CitySaveDTO;
import com.project.webapp.area.service.CityService;
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
public class CityController {

    @Autowired
    private CityService cityService;

    @PostMapping("/city")
    public ResponseEntity<ResponseDTO<CitySearchDTO>> createCity(
            @Valid @RequestBody CitySaveDTO citySaveDTO
    ) {
        CitySearchDTO dto = cityService.create(citySaveDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDTO<>(HttpStatus.CREATED.value(), dto));
    }

    @GetMapping("/cities")
    public ResponseEntity<ResponseDTO<List<CitySearchByCountryIdDTO>>> readCitiesByCountryId(
            @RequestParam(name = "country_id") @NotBlank Integer countryId
    ) {
        List<CitySearchByCountryIdDTO> dtoList = cityService.findByCountryId(countryId);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dtoList));
    }

    @PutMapping("/city/{id}")
    public ResponseEntity<ResponseDTO<CitySearchDTO>> updateCitise(
            @PathVariable @NotBlank Integer id,
            @Valid @RequestBody CitySaveDTO cityIdNameCountryIdDTO
    ) {
        CitySearchDTO dto = cityService.update(id, cityIdNameCountryIdDTO);

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