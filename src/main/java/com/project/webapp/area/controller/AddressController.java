package com.project.webapp.area.controller;


import com.project.webapp.area.dto.AddressSaveDTO;
import com.project.webapp.area.dto.AddressSearchDTO;
import com.project.webapp.area.service.AddressService;
import com.project.webapp.config.ApiVersionController;
import com.project.webapp.config.dto.ResponseDTO;
import com.project.webapp.config.dto.ResponseSuccessDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ApiVersionController.URL_API_VERSION)
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/address")
    public ResponseEntity<ResponseDTO<AddressSearchDTO>> createAddress(
            @Valid @RequestBody AddressSaveDTO addressSaveDTO
    ) {
        AddressSearchDTO dto = addressService.create(addressSaveDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDTO<>(HttpStatus.CREATED.value(), dto));
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<ResponseDTO<AddressSearchDTO>> readAddress(
            @PathVariable @NotBlank Integer id
    ) {
        AddressSearchDTO dto = addressService.findById(id);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dto));
    }

    @PutMapping("/address/{id}")
    public ResponseEntity<ResponseDTO<AddressSearchDTO>> updateAddress(
            @PathVariable @NotBlank Integer id,
            @Valid @RequestBody AddressSaveDTO addressSaveDTO
    ) {
        AddressSearchDTO dto = addressService.update(id, addressSaveDTO);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dto));
    }

    @DeleteMapping("/address/{id}")
    public ResponseEntity<ResponseDTO<Integer>> deleteAddress(
            @PathVariable @NotBlank Integer id
    ) {
        addressService.delete(id);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.NO_CONTENT.value()));
    }
}