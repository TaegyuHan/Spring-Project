package com.project.webapp.film.controller;

import com.project.webapp.config.ApiVersionController;
import com.project.webapp.config.dto.ResponseDTO;
import com.project.webapp.config.dto.ResponseSuccessDTO;
import com.project.webapp.film.dto.ActorSaveDTO;
import com.project.webapp.film.dto.ActorSearchDTO;
import com.project.webapp.film.service.ActorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(ApiVersionController.URL_API_VERSION)
public class ActorController {
    
    @Autowired
    private ActorService actorService;

    @PostMapping("/actor")
    public ResponseEntity<ResponseDTO<ActorSearchDTO>> createActor(
            @Valid @RequestBody ActorSaveDTO ActorSaveDTO
    ) {
        ActorSearchDTO dto = actorService.create(ActorSaveDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseSuccessDTO<>(HttpStatus.CREATED.value(), dto));
    }

    @GetMapping("/actor/{id}")
    public ResponseEntity<ResponseDTO<ActorSearchDTO>> readActor(
            @PathVariable @NotBlank Integer id
    ) {
        ActorSearchDTO dto = actorService.findByid(id);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dto));
    }

    @PutMapping("/actor/{id}")
    public ResponseEntity<ResponseDTO<ActorSearchDTO>> updateActor(
            @PathVariable @NotBlank Integer id,
            @Valid @RequestBody ActorSaveDTO ActorSaveDTO
    ) {
        ActorSearchDTO dto = actorService.update(id, ActorSaveDTO);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.OK.value(), dto));
    }

    @DeleteMapping("/actor/{id}")
    public ResponseEntity<ResponseDTO<Integer>> deleteActor(
            @PathVariable @NotBlank Integer id
    ) {
        actorService.delete(id);

        return ResponseEntity.ok()
                .body(new ResponseSuccessDTO<>(HttpStatus.NO_CONTENT.value()));
    }
}