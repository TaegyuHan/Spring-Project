package com.project.webapp.film.service;

import com.project.webapp.config.exception.NonExistentDataException;
import com.project.webapp.film.dto.ActorSearchDTO;
import com.project.webapp.film.dto.CategorySearchDTO;
import com.project.webapp.film.dto.FilmSaveDTO;
import com.project.webapp.film.dto.FilmSearchDTO;
import com.project.webapp.film.entity.*;
import com.project.webapp.film.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class FilmService {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private FilmCategoryRepository filmCategoryRepository;

    @Autowired
    private FilmActorRepository filmActorRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ModelMapper modelMapper;

    public FilmSearchDTO create(FilmSaveDTO filmSaveDTO) {

        Optional<Language> language = languageRepository.findById(filmSaveDTO.getLanguageId());
        if (language.isEmpty()) {
            throw new NonExistentDataException("Data does not exist. languageId", filmSaveDTO);
        }

        modelMapper.typeMap(FilmSaveDTO.class, Film.class).addMappings(mapper -> {
            mapper.skip(Film::setFilmId);
        });

        Film newEntity = modelMapper.map(filmSaveDTO, Film.class);
        newEntity.setLanguage(language.get());

        if (filmSaveDTO.getOriginalLanguageId() != null) {
            Optional<Language> originalLanguage = languageRepository.findById(filmSaveDTO.getLanguageId());
            if (originalLanguage.isEmpty()) {
                throw new NonExistentDataException("Data does not exist. originalLanguageId", filmSaveDTO);
            }
            newEntity.setOriginalLanguage(originalLanguage.get());
        }
        Film savedEntity = filmRepository.save(newEntity);

        List<Integer> categoryIds = filmSaveDTO.getCategoryIds();
        List<FilmCategory> filmCategories = new ArrayList<>(categoryIds.size());
        for (Integer categoryId : categoryIds) {
            filmCategories.add(
                    FilmCategory.builder()
                            .id(new FilmCategoryId(savedEntity.getFilmId(), categoryId))
                            .category(new Category(categoryId))
                            .film(savedEntity).build()
            );
        }
        List<CategorySearchDTO> savedCategories = filmCategoryRepository.saveAll(filmCategories).stream()
                .map(filmCategory -> modelMapper.map(filmCategory.getCategory(), CategorySearchDTO.class))
                .toList();

        List<Integer> actorIds = filmSaveDTO.getActorIds();
        List<FilmActor> filmActors = new ArrayList<>(actorIds.size());
        for (Integer actorId : actorIds) {
            filmActors.add(
                    FilmActor.builder()
                            .id(new FilmActorId(savedEntity.getFilmId(), actorId))
                            .actor(new Actor(actorId))
                            .film(savedEntity).build()
            );
        }
        List<ActorSearchDTO> savedActorSearchDTOs = filmActorRepository.saveAll(filmActors).stream()
                .map(filmActor -> modelMapper.map(filmActor.getActor(), ActorSearchDTO.class))
                .toList();

        FilmSearchDTO filmSearchDTO = modelMapper.map(savedEntity, FilmSearchDTO.class);
        filmSearchDTO.setCategorySearchDTOs(savedCategories);
        filmSearchDTO.setActorSearchDTOs(savedActorSearchDTOs);
        return filmSearchDTO;
    }

    @Transactional(readOnly = true)
    public FilmSearchDTO findById(Integer id) {

        Optional<Film> film = filmRepository.findById(id);
        if (film.isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }
        FilmSearchDTO filmSearchDTO = modelMapper.map(film, FilmSearchDTO.class);

        List<CategorySearchDTO> categorySearchDTOs = filmCategoryRepository.findById_FilmId(id).stream()
                .map(filmCategory -> modelMapper.map(filmCategory.getCategory(), CategorySearchDTO.class))
                .toList();

        List<ActorSearchDTO> actorSearchDTOs = filmActorRepository.findById_FilmId(id).stream()
                .map(filmActor -> modelMapper.map(filmActor.getActor(), ActorSearchDTO.class))
                .toList();

        filmSearchDTO.setCategorySearchDTOs(categorySearchDTOs);
        filmSearchDTO.setActorSearchDTOs(actorSearchDTOs);
        return filmSearchDTO;
    }

    public FilmSearchDTO update(Integer id, FilmSaveDTO filmSaveDTO) {

        Optional<Film> film = filmRepository.findById(id);
        if (film.isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }
        Film updateEntity = film.get();

        Optional<Language> language = languageRepository.findById(filmSaveDTO.getLanguageId());
        if (language.isEmpty()) {
            throw new NonExistentDataException("Data does not exist. languageId", filmSaveDTO);
        }
        updateEntity.setLanguage(language.get());

        if (filmSaveDTO.getOriginalLanguageId() != null) {
            Optional<Language> originalLanguage = languageRepository.findById(filmSaveDTO.getLanguageId());
            if (originalLanguage.isEmpty()) {
                throw new NonExistentDataException("Data does not exist. originalLanguageId", filmSaveDTO);
            }
            updateEntity.setOriginalLanguage(originalLanguage.get());
        } else {
            updateEntity.setOriginalLanguage(null);
        }

        BeanUtils.copyProperties(filmSaveDTO, updateEntity,
                "languageId", "originalLanguageId", "specialFeatures");

        Film savedEntity = filmRepository.save(updateEntity);

        filmCategoryRepository.deleteById_FilmId(id);
        filmActorRepository.deleteById_FilmId(id);

        List<Integer> categoryIds = filmSaveDTO.getCategoryIds();
        List<FilmCategory> filmCategories = new ArrayList<>(categoryIds.size());
        for (Integer categoryId : categoryIds) {
            filmCategories.add(
                    FilmCategory.builder()
                            .id(new FilmCategoryId(savedEntity.getFilmId(), categoryId))
                            .category(new Category(categoryId))
                            .film(savedEntity).build()
            );
        }
        List<CategorySearchDTO> savedCategories = filmCategoryRepository.saveAll(filmCategories).stream()
                .map(filmCategory -> modelMapper.map(filmCategory.getCategory(), CategorySearchDTO.class))
                .toList();

        List<Integer> actorIds = filmSaveDTO.getActorIds();
        List<FilmActor> filmActors = new ArrayList<>(actorIds.size());
        for (Integer actorId : actorIds) {
            filmActors.add(
                    FilmActor.builder()
                            .id(new FilmActorId(savedEntity.getFilmId(), actorId))
                            .actor(new Actor(actorId))
                            .film(savedEntity).build()
            );
        }
        List<ActorSearchDTO> savedActorSearchDTOs = filmActorRepository.saveAll(filmActors).stream()
                .map(filmActor -> modelMapper.map(filmActor.getActor(), ActorSearchDTO.class))
                .toList();

        FilmSearchDTO filmSearchDTO = modelMapper.map(savedEntity, FilmSearchDTO.class);
        filmSearchDTO.setCategorySearchDTOs(savedCategories);
        filmSearchDTO.setActorSearchDTOs(savedActorSearchDTOs);
        return filmSearchDTO;
    }

    public void delete(Integer id) {
        filmCategoryRepository.deleteById_FilmId(id);
        filmActorRepository.deleteById_FilmId(id);
        filmRepository.deleteById(id);
    }
}