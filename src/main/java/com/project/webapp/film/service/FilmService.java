package com.project.webapp.film.service;

import com.project.webapp.config.exception.NonExistentDataException;
import com.project.webapp.film.dto.FilmSaveDTO;
import com.project.webapp.film.dto.FilmSearchDTO;
import com.project.webapp.film.entity.Film;
import com.project.webapp.film.entity.Language;
import com.project.webapp.film.repository.FilmRepository;
import com.project.webapp.film.repository.LanguageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class FilmService {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    LanguageRepository languageRepository;

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
        return modelMapper.map(savedEntity, FilmSearchDTO.class);
    }

    @Transactional(readOnly = true)
    public FilmSearchDTO findById(Integer id) {

        Optional<Film> film = filmRepository.findById(id);
        if (film.isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }

        return modelMapper.map(film, FilmSearchDTO.class);
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
        return modelMapper.map(savedEntity, FilmSearchDTO.class);
    }

    public void delete(Integer id) {
        if (!filmRepository.existsById(id)) {
            throw new NonExistentDataException("FilmId not found", id);
        }
        filmRepository.deleteById(id);
    }
}