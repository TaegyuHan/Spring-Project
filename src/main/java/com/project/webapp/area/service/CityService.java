package com.project.webapp.area.service;

import com.project.webapp.area.dto.CitySearchDTO;
import com.project.webapp.area.dto.CitySearchByCountryIdDTO;
import com.project.webapp.area.dto.CitySaveDTO;
import com.project.webapp.area.entity.City;
import com.project.webapp.area.repository.CityRepository;
import com.project.webapp.area.repository.CountryRepository;
import com.project.webapp.config.exception.AlreadyExistsDataException;
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
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CitySearchDTO create(CitySaveDTO citySaveDTO) {

        if (cityRepository.existsByCountry_CountryIdAndCity(citySaveDTO.getCountryId(), citySaveDTO.getCity())) {
            throw new AlreadyExistsDataException("The name of the city already exists.", citySaveDTO);
        }

        modelMapper.typeMap(CitySaveDTO.class, City.class).addMappings(mapper -> {
            mapper.skip(City::setCityId);
            mapper.map(CitySaveDTO::getCity, City::setCity);
        });

        City newEntity = modelMapper.map(citySaveDTO, City.class);
        newEntity.setCountry(countryRepository.getReferenceById(citySaveDTO.getCountryId()));
        City savedEntity = cityRepository.save(newEntity);

        return modelMapper.map(savedEntity, CitySearchDTO.class);
    }

    @Transactional(readOnly = true)
    public List<CitySearchByCountryIdDTO> findByCountryId(Integer countryId) {

        if (!countryRepository.existsById(countryId)) {
            throw new NonExistentDataException("countryId does not exist.");
        }

        List<City> entityList = cityRepository.findByCountry_CountryId(countryId);

        Type listType = new TypeToken<List<CitySearchByCountryIdDTO>>() {}.getType();

        return modelMapper.map(entityList, listType);
    }

    public CitySearchDTO update(Integer id, CitySaveDTO citySaveDTO) {

        City updateEntity = cityRepository.getReferenceById(id);

        if (cityRepository.existsByCountry_CountryIdAndCity(citySaveDTO.getCountryId(), citySaveDTO.getCity())) {
            throw new AlreadyExistsDataException("The name of the city already exists.", citySaveDTO);
        }

        updateEntity.setCity(citySaveDTO.getCity());
        updateEntity.setCityId(citySaveDTO.getCountryId());
        City savedEntity = cityRepository.save(updateEntity);

        return modelMapper.map(savedEntity, CitySearchDTO.class);
    }

    public void delete(Integer id) {
        cityRepository.deleteById(id);
    }
}