package com.project.webapp.area.service;

import com.project.webapp.area.dto.CityIdNameCountryIdDTO;
import com.project.webapp.area.dto.CityIdNameDTO;
import com.project.webapp.area.dto.CityNameCountryIdDTO;
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

    public CityIdNameCountryIdDTO create(CityNameCountryIdDTO cityNameCountryIdDTO) {

        if (cityRepository.findByCountry_CountryIdAndCity(cityNameCountryIdDTO.getCountryId(), cityNameCountryIdDTO.getCity())
                .isPresent()) { // 중복 데이터 검사
            throw new AlreadyExistsDataException("The name of the city already exists.", cityNameCountryIdDTO);
        }

        Optional<Country> country = countryRepository.findById(cityNameCountryIdDTO.getCountryId());
        if (country.isEmpty()) {
            throw new NonExistentDataException("CountryId not found", cityNameCountryIdDTO);
        }

        modelMapper.typeMap(CityNameCountryIdDTO.class, City.class).addMappings(mapper -> {
            mapper.skip(City::setCityId);
            mapper.map(CityNameCountryIdDTO::getCity, City::setCity);
        });

        City newEntity = modelMapper.map(cityNameCountryIdDTO, City.class);
        newEntity.setCountry(country.get());
        City savedEntity = cityRepository.save(newEntity);

        return modelMapper.map(savedEntity, CityIdNameCountryIdDTO.class);
    }

    public List<CityIdNameDTO> findByCountryId(Integer countryId) {

        // country 존재 확인
        if (countryRepository.findById(countryId).isEmpty()) {
            throw new NonExistentDataException("country does not exist.");
        }

        List<City> entityList = cityRepository.findByCountry_CountryId(countryId);

        Type listType = new TypeToken<List<CityIdNameDTO>>() {}.getType();

        modelMapper.typeMap(City.class, CityIdNameDTO.class).addMappings(mapper -> {
            mapper.map(City::getCity, CityIdNameDTO::setCity);
            mapper.map(City::getCity, CityIdNameDTO::setCity);
        });

        return modelMapper.map(entityList, listType);
    }

    public CityIdNameCountryIdDTO update(Integer id, CityNameCountryIdDTO cityIdNameCountryIdDTO) {
        Optional<City> check;

        // city 존재하지 않음
        check = cityRepository.findById(id);
        if (check.isEmpty()) {
            throw new NonExistentDataException("city does not exist.");
        }
        City findEntity = check.get();

        // 이미 존재합니다.
        check = cityRepository.findByCountry_CountryIdAndCity(
                cityIdNameCountryIdDTO.getCountryId(), cityIdNameCountryIdDTO.getCity());
        if (check.isPresent()) {
            throw new AlreadyExistsDataException("Data already exists.", cityIdNameCountryIdDTO);
        }
        findEntity.setCity(cityIdNameCountryIdDTO.getCity());
        findEntity.setCityId(cityIdNameCountryIdDTO.getCountryId());
        City savedEntity = cityRepository.save(findEntity);


        modelMapper.typeMap(City.class, CityNameCountryIdDTO.class).addMappings(mapper -> {
            mapper.map(City::getCityId, CityNameCountryIdDTO::setCity);
            mapper.map(City::getCity, CityNameCountryIdDTO::setCity);
            mapper.map(src -> src.getCountry().getCountryId(),
                    CityNameCountryIdDTO::setCountryId);
        });
        return modelMapper.map(savedEntity, CityIdNameCountryIdDTO.class);
    }

    public void delete(Integer id) {
        if (cityRepository.findById(id).isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }
        cityRepository.deleteById(id);
    }
}