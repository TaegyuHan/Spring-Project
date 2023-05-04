package com.project.webapp.area.service;


import com.project.webapp.area.dto.CountrySearchDTO;
import com.project.webapp.area.dto.CountrySaveDTO;
import com.project.webapp.area.entity.Country;
import com.project.webapp.area.repository.CountryRepository;
import com.project.webapp.config.exception.AlreadyExistsDataException;
import com.project.webapp.config.exception.NonExistentDataException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;


@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CountrySearchDTO create(CountrySaveDTO countryDTO) {

        if (countryRepository.findByCountry(countryDTO.getCountry()).isPresent()) { // 중복 데이터 검사
            throw new AlreadyExistsDataException("The name of the country already exists.", countryDTO);
        }

        Country newEntity = modelMapper.map(countryDTO, Country.class);
        Country savedEntity = countryRepository.save(newEntity);
        return modelMapper.map(savedEntity, CountrySearchDTO.class);
    }

    public List<CountrySearchDTO> readAll() {
        List<Country> entityList = countryRepository.findAll();

        Type listType = new TypeToken<List<CountrySearchDTO>>() {}.getType();
        return modelMapper.map(entityList, listType);
    }

    public CountrySearchDTO update(Integer id, CountrySaveDTO countryDTO) {
        Optional<Country> check;

        // 업데이트 데이터 존재 확인
        check = countryRepository.findByCountry(countryDTO.getCountry());
        if (check.isPresent()) {
            throw new AlreadyExistsDataException("Data already exists.", countryDTO);
        }

        // 데이터 존재 하는지 확인
        check = countryRepository.findById(id);
        if (check.isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }

        Country updateEntity = check.get();
        updateEntity.setCountry(countryDTO.getCountry());
        Country savedEntity = countryRepository.save(updateEntity);
        return modelMapper.map(savedEntity, CountrySearchDTO.class);
    }

    public void delete(Integer id) {
        if (countryRepository.findById(id).isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }
        countryRepository.deleteById(id);
    }
}