package com.project.webapp.film.service;

import com.project.webapp.config.exception.AlreadyExistsDataException;
import com.project.webapp.config.exception.NonExistentDataException;
import com.project.webapp.film.dto.LanguageSearchDTO;
import com.project.webapp.film.dto.LanguageSaveDTO;
import com.project.webapp.film.entity.Language;
import com.project.webapp.film.repository.LanguageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    private ModelMapper modelMapper;

    public LanguageSearchDTO create(LanguageSaveDTO languageSaveDTO) {
        if (languageRepository.findByName(languageSaveDTO.getName()).isPresent()) {
            throw new AlreadyExistsDataException("The name of the language already exists.", languageSaveDTO);
        }

        Language newEntity = modelMapper.map(languageSaveDTO, Language.class);
        Language savedEntity = languageRepository.save(newEntity);
        return modelMapper.map(savedEntity, LanguageSearchDTO.class);
    }

    public LanguageSearchDTO findByid(Integer id) {
        // 존재 유무 확인
        Optional<Language> category = languageRepository.findById(id);
        if (category.isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }

        return modelMapper.map(category.get(), LanguageSearchDTO.class);
    }

    public LanguageSearchDTO update(Integer id, LanguageSaveDTO languageSaveDTO) {
        Optional<Language> check;
        // 업데이트 데이터 존재 확인
        check = languageRepository.findByName(languageSaveDTO.getName());
        if (check.isPresent()) {
            throw new AlreadyExistsDataException("Data already exists.", languageSaveDTO);
        }

        // 데이터 존재 하는지 확인
        check = languageRepository.findById(id);
        if (check.isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }

        Language updateEntity = check.get();
        updateEntity.setName(languageSaveDTO.getName());
        Language savedEntity = languageRepository.save(updateEntity);
        return modelMapper.map(savedEntity, LanguageSearchDTO.class);
    }

    public void delete(Integer id) {
        if (languageRepository.findById(id).isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }
        languageRepository.deleteById(id);
    }
}