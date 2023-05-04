package com.project.webapp.film.service;

import com.project.webapp.config.exception.NonExistentDataException;
import com.project.webapp.film.dto.ActorSaveDTO;
import com.project.webapp.film.dto.ActorSearchDTO;
import com.project.webapp.film.entity.Actor;
import com.project.webapp.film.repository.ActorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActorService {

    @Autowired
    ActorRepository actorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ActorSearchDTO create(ActorSaveDTO actorSaveDTO) {
        Actor newEntity = modelMapper.map(actorSaveDTO, Actor.class);
        Actor savedEntity = actorRepository.save(newEntity);
        return modelMapper.map(savedEntity, ActorSearchDTO.class);
    }

    public ActorSearchDTO findByid(Integer id) {
        // 존재 유무 확인
        Optional<Actor> actor = actorRepository.findById(id);
        if (actor.isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }

        return modelMapper.map(actor, ActorSearchDTO.class);
    }

    public ActorSearchDTO update(Integer id, ActorSaveDTO actorSaveDTO) {
        Optional<Actor> check = actorRepository.findById(id);
        if (check.isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }

        Actor updateEntity = check.get();
        updateEntity.setFirstName(actorSaveDTO.getFirstName());
        updateEntity.setLastName(actorSaveDTO.getLastName());
        Actor savedEntity = actorRepository.save(updateEntity);
        return modelMapper.map(savedEntity, ActorSearchDTO.class);
    }

    public void delete(Integer id) {
        if (actorRepository.findById(id).isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }
        actorRepository.deleteById(id);
    }
}