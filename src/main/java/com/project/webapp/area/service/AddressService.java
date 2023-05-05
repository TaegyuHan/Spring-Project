package com.project.webapp.area.service;

import com.project.webapp.area.dto.AddressSaveDTO;
import com.project.webapp.area.dto.AddressSearchDTO;
import com.project.webapp.area.entity.Address;
import com.project.webapp.area.entity.City;
import com.project.webapp.area.repository.AddressRepository;
import com.project.webapp.area.repository.CityRepository;
import com.project.webapp.config.exception.NonExistentDataException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AddressService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AddressSearchDTO create(AddressSaveDTO addressSaveDTO) {

        City city = cityRepository.findById(addressSaveDTO.getCityId())
                .orElseThrow(() -> new NonExistentDataException("Data does not exist. CityId", addressSaveDTO));

        modelMapper.typeMap(AddressSaveDTO.class, Address.class)
                .addMappings(mapper -> mapper.skip(Address::setAddressId));

        Address newEntity = modelMapper.map(addressSaveDTO, Address.class);
        newEntity.setCity(city);
        Address savedEntity = addressRepository.save(newEntity);

        return modelMapper.map(savedEntity, AddressSearchDTO.class);
    }

    @Transactional(readOnly = true)
    public AddressSearchDTO findById(Integer id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new NonExistentDataException("AddressId not found", id));

        return modelMapper.map(address, AddressSearchDTO.class);
    }

    public AddressSearchDTO update(Integer id, AddressSaveDTO addressSaveDTO) {

        Address updateEntity = addressRepository.findById(id)
                .orElseThrow(() -> new NonExistentDataException("AddressId not found", id));

        City city = cityRepository.findById(addressSaveDTO.getCityId())
                .orElseThrow(() -> new NonExistentDataException("Data does not exist. CityId", addressSaveDTO));

        updateEntity.setCity(city);

        modelMapper.typeMap(AddressSaveDTO.class, Address.class)
                .addMappings(mapper -> mapper.skip(Address::setAddressId));

        modelMapper.map(addressSaveDTO, updateEntity);
        Address savedEntity = addressRepository.save(updateEntity);

        return modelMapper.map(savedEntity, AddressSearchDTO.class);
    }

    public void delete(Integer id) {
        addressRepository.deleteById(id);
    }
}