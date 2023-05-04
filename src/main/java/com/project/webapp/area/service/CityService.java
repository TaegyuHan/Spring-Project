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

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CitySearchDTO create(CitySaveDTO citySaveDTO) {

        // 중복 데이터 검사
        if (cityRepository.findByCountry_CountryIdAndCity(citySaveDTO.getCountryId(), citySaveDTO.getCity())
                .isPresent()) {
            throw new AlreadyExistsDataException("The name of the city already exists.", citySaveDTO);
        }

        // 나라 존재 확인
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

    public List<CitySearchByCountryIdDTO> findByCountryId(Integer countryId) {

        // country 존재 확인
        if (countryRepository.findById(countryId).isEmpty()) {
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

    public CitySearchDTO update(Integer id, CitySaveDTO cityIdNameCountryIdDTO) {
        Optional<City> check;

        // city 존재하지 않음
        check = cityRepository.findById(id);
        if (check.isEmpty()) {
            throw new NonExistentDataException("city does not exist.");
        }
        City updateEntity = check.get();

        // 이미 존재합니다.
        check = cityRepository.findByCountry_CountryIdAndCity(
                cityIdNameCountryIdDTO.getCountryId(), cityIdNameCountryIdDTO.getCity());
        if (check.isPresent()) {
            throw new AlreadyExistsDataException("Data already exists.", cityIdNameCountryIdDTO);
        }
        updateEntity.setCity(cityIdNameCountryIdDTO.getCity());
        updateEntity.setCityId(cityIdNameCountryIdDTO.getCountryId());
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
        if (cityRepository.findById(id).isEmpty()) {
            throw new NonExistentDataException("CityId not found", id);
        }
        cityRepository.deleteById(id);
    }
}