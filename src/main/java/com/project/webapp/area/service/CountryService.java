package com.project.webapp.area.service;


import com.project.webapp.area.dto.CountrySearchDTO;
import com.project.webapp.area.dto.CountrySaveDTO;
import com.project.webapp.area.entity.Country;
import com.project.webapp.area.repository.CountryRepository;
import com.project.webapp.config.exception.NonExistentDataException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.List;


@Service
@Transactional
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CountrySearchDTO create(CountrySaveDTO countryDTO) {
        Country newEntity = modelMapper.map(countryDTO, Country.class);
        Country savedEntity = countryRepository.save(newEntity);
        return modelMapper.map(savedEntity, CountrySearchDTO.class);
    }

    @Transactional(readOnly = true)
    public List<CountrySearchDTO> readAll() {
        List<Country> entityList = countryRepository.findAll();

        Type listType = new TypeToken<List<CountrySearchDTO>>() {}.getType();

        return modelMapper.map(entityList, listType);
    }

    public CountrySearchDTO update(Integer id, CountrySaveDTO countryDTO) {

        Country country = countryRepository.findById(id)
                .orElseThrow(() -> new NonExistentDataException("Data does not exist.", id));

        country.setCountry(countryDTO.getCountry());
        Country savedEntity = countryRepository.save(country);

        return modelMapper.map(savedEntity, CountrySearchDTO.class);
    }

    public void delete(Integer id) {
        countryRepository.deleteById(id);
    }
}