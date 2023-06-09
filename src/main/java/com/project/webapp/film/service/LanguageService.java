package com.project.webapp.film.service;

import com.project.webapp.config.exception.NonExistentDataException;
import com.project.webapp.film.dto.LanguageSearchDTO;
import com.project.webapp.film.dto.LanguageSaveDTO;
import com.project.webapp.film.entity.Language;
import com.project.webapp.film.repository.LanguageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private ModelMapper modelMapper;

    public LanguageSearchDTO create(LanguageSaveDTO languageSaveDTO) {
        Language newEntity = modelMapper.map(languageSaveDTO, Language.class);
        Language savedEntity = languageRepository.save(newEntity);
        return modelMapper.map(savedEntity, LanguageSearchDTO.class);
    }

    @Transactional(readOnly = true)
    public LanguageSearchDTO findByid(Integer id) {

        Optional<Language> category = languageRepository.findById(id);
        if (category.isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }

        return modelMapper.map(category.get(), LanguageSearchDTO.class);
    }

    public LanguageSearchDTO update(Integer id, LanguageSaveDTO languageSaveDTO) {
        Optional<Language> check = languageRepository.findById(id);
        if (check.isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }

        Language updateEntity = check.get();
        updateEntity.setName(languageSaveDTO.getName());
        Language savedEntity = languageRepository.save(updateEntity);
        return modelMapper.map(savedEntity, LanguageSearchDTO.class);
    }

    public void delete(Integer id) {
        languageRepository.deleteById(id);
    }
}