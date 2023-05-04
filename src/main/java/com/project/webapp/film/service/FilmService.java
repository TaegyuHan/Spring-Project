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

        Language language = languageRepository.findById(filmSaveDTO.getLanguageId())
                .orElseThrow(() -> new NonExistentDataException("Data does not exist. languageId", filmSaveDTO));

        modelMapper.typeMap(FilmSaveDTO.class, Film.class)
                .addMappings(mapper -> mapper.skip(Film::setFilmId));

        Film newEntity = modelMapper.map(filmSaveDTO, Film.class);
        newEntity.setLanguage(language);

        if (filmSaveDTO.getOriginalLanguageId() != null) {
            Language originalLanguage = languageRepository.findById(filmSaveDTO.getOriginalLanguageId())
                    .orElseThrow(() -> new NonExistentDataException("Data does not exist. originalLanguageId", filmSaveDTO));
            newEntity.setOriginalLanguage(originalLanguage);
        }

        Film savedEntity = filmRepository.save(newEntity);
        return modelMapper.map(savedEntity, FilmSearchDTO.class);
    }

    @Transactional(readOnly = true)
    public FilmSearchDTO findById(Integer id) {

        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new NonExistentDataException("Data does not exist.", id));

        return modelMapper.map(film, FilmSearchDTO.class);
    }

    public FilmSearchDTO update(Integer id, FilmSaveDTO filmSaveDTO) {

        Film updateEntity = filmRepository.findById(id)
                .orElseThrow(() -> new NonExistentDataException("Data does not exist.", id));

        Language language = languageRepository.findById(filmSaveDTO.getLanguageId())
                .orElseThrow(() -> new NonExistentDataException("Data does not exist. languageId", filmSaveDTO));
        updateEntity.setLanguage(language);

        if (filmSaveDTO.getOriginalLanguageId() != null) {
            Language originalLanguage = languageRepository.findById(filmSaveDTO.getOriginalLanguageId())
                    .orElseThrow(() -> new NonExistentDataException("Data does not exist. originalLanguageId", filmSaveDTO));
            updateEntity.setOriginalLanguage(originalLanguage);
        } else {
            updateEntity.setOriginalLanguage(null);
        }

        BeanUtils.copyProperties(filmSaveDTO, updateEntity, "languageId", "originalLanguageId", "specialFeatures");

        Film savedEntity = filmRepository.save(updateEntity);
        return modelMapper.map(savedEntity, FilmSearchDTO.class);
    }

    public void delete(Integer id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new NonExistentDataException("Data does not exist.", id));

        filmRepository.delete(film);
    }
}