package com.project.webapp.film.service;

import com.project.webapp.config.exception.NonExistentDataException;
import com.project.webapp.film.dto.ActorSaveDTO;
import com.project.webapp.film.dto.ActorSearchDTO;
import com.project.webapp.film.entity.Actor;
import com.project.webapp.film.repository.ActorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ActorSearchDTO create(ActorSaveDTO actorSaveDTO) {
        Actor newEntity = modelMapper.map(actorSaveDTO, Actor.class);
        Actor savedEntity = actorRepository.save(newEntity);
        return modelMapper.map(savedEntity, ActorSearchDTO.class);
    }

    @Transactional(readOnly = true)
    public ActorSearchDTO findById(Integer id) {
        return actorRepository.findById(id)
                .map(actor -> modelMapper.map(actor, ActorSearchDTO.class))
                .orElseThrow(() -> new NonExistentDataException("Data does not exist.", id));
    }


    public ActorSearchDTO update(Integer id, ActorSaveDTO actorSaveDTO) {

        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new NonExistentDataException("Data does not exist.", id));

        actor.setFirstName(actorSaveDTO.getFirstName());
        actor.setLastName(actorSaveDTO.getLastName());
        Actor savedEntity = actorRepository.save(actor);
        return modelMapper.map(savedEntity, ActorSearchDTO.class);
    }

    public void delete(Integer id) { actorRepository.deleteById(id); }
}