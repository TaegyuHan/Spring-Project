package com.project.webapp.area.service;

import com.project.webapp.area.dto.CitySearchDTO;
import com.project.webapp.area.dto.CitySearchByCountryIdDTO;
import com.project.webapp.area.dto.CitySaveDTO;
import com.project.webapp.area.entity.City;
import com.project.webapp.area.entity.Country;
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
import java.util.Optional;

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

        Optional<Country> country = countryRepository.findById(citySaveDTO.getCountryId());
        if (country.isEmpty()) {
            throw new NonExistentDataException("CountryId not found", citySaveDTO);
        }

        modelMapper.typeMap(CitySaveDTO.class, City.class).addMappings(mapper -> {
            mapper.skip(City::setCityId);
            mapper.map(CitySaveDTO::getCity, City::setCity);
        });

        City newEntity = modelMapper.map(citySaveDTO, City.class);
        newEntity.setCountry(country.get());
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

        modelMapper.typeMap(City.class, CitySearchByCountryIdDTO.class).addMappings(mapper -> {
            mapper.map(City::getCity, CitySearchByCountryIdDTO::setCity);
            mapper.map(City::getCity, CitySearchByCountryIdDTO::setCity);
        });

        return modelMapper.map(entityList, listType);
    }

    public CitySearchDTO update(Integer id, CitySaveDTO citySaveDTO) {

        Optional<City> city = cityRepository.findById(id);
        if (city.isEmpty()) {
            throw new NonExistentDataException("city does not exist.");
        }
        City updateEntity = city.get();

        if (cityRepository.existsByCountry_CountryIdAndCity(citySaveDTO.getCountryId(), citySaveDTO.getCity())) {
            throw new AlreadyExistsDataException("The name of the city already exists.", citySaveDTO);
        }

        updateEntity.setCity(citySaveDTO.getCity());
        updateEntity.setCityId(citySaveDTO.getCountryId());
        City savedEntity = cityRepository.save(updateEntity);

        modelMapper.typeMap(City.class, CitySaveDTO.class).addMappings(mapper -> {
            mapper.map(City::getCityId, CitySaveDTO::setCity);
            mapper.map(City::getCity, CitySaveDTO::setCity);
            mapper.map(src -> src.getCountry().getCountryId(),
                    CitySaveDTO::setCountryId);
        });
        return modelMapper.map(savedEntity, CitySearchDTO.class);
    }

    public void delete(Integer id) {
        if (!cityRepository.existsById(id)) {
            throw new NonExistentDataException("CityId not found", id);
        }
        cityRepository.deleteById(id);
    }
}