package com.project.webapp.area.service;


import com.project.webapp.area.dto.CountryIdNameDTO;
import com.project.webapp.area.dto.CountryNameDTO;
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

    public CountryIdNameDTO create(CountryNameDTO countryDTO) {

        if (countryRepository.findByCountry(countryDTO.getCountry()).isPresent()) { // 중복 데이터 검사
            throw new AlreadyExistsDataException("The name of the country already exists.", countryDTO);
        }

        Country newEntity = modelMapper.map(countryDTO, Country.class);
        Country savedEntity = countryRepository.save(newEntity);
        return modelMapper.map(savedEntity, CountryIdNameDTO.class);
    }

    public List<CountryIdNameDTO> readAll() {
        List<Country> entityList = countryRepository.findAll();

        Type listType = new TypeToken<List<CountryIdNameDTO>>() {}.getType();
        return modelMapper.map(entityList, listType);
    }

    public CountryIdNameDTO update(Integer id, CountryNameDTO countryDTO) {
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

        Country findEntity = check.get();
        findEntity.setCountry(countryDTO.getCountry());
        Country savedEntity = countryRepository.save(findEntity);
        return modelMapper.map(savedEntity, CountryIdNameDTO.class);
    }

    public void delete(Integer id) {
        if (countryRepository.findById(id).isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }
        countryRepository.deleteById(id);
    }
}