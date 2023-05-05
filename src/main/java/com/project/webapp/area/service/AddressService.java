package com.project.webapp.area.service;

import com.project.webapp.area.dto.AddressSaveDTO;
import com.project.webapp.area.dto.AddressSearchDTO;
import com.project.webapp.area.entity.Address;
import com.project.webapp.area.entity.City;
import com.project.webapp.area.repository.AddressRepository;
import com.project.webapp.area.repository.CityRepository;
import com.project.webapp.config.exception.NonExistentDataException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

        Optional<City> city = cityRepository.findById(addressSaveDTO.getCityId());
        if (city.isEmpty()) {
            throw new NonExistentDataException("Data does not exist. CityId", addressSaveDTO);
        }

        modelMapper.typeMap(AddressSaveDTO.class, Address.class)
                .addMappings(mapper -> mapper.skip(Address::setAddressId));

        Address newEntity = modelMapper.map(addressSaveDTO, Address.class);
        newEntity.setCity(city.get());
        Address savedEntity = addressRepository.save(newEntity);

        return modelMapper.map(savedEntity, AddressSearchDTO.class);
    }

    @Transactional(readOnly = true)
    public AddressSearchDTO findById(Integer id) {

        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()) {
            throw new NonExistentDataException("AddressId not found", id);
        }

        return modelMapper.map(address, AddressSearchDTO.class);
    }

    public AddressSearchDTO update(Integer id, AddressSaveDTO addressSaveDTO) {

        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()) {
            throw new NonExistentDataException("AddressId not found", id);
        }
        Address updateEntity = address.get();

        Optional<City> city = cityRepository.findById(addressSaveDTO.getCityId());
        if (city.isEmpty()) {
            throw new NonExistentDataException("Data does not exist. CityId", addressSaveDTO);
        }

        updateEntity.setCity(city.get());

        BeanUtils.copyProperties(addressSaveDTO, updateEntity,
                "addressId", "city");

        Address savedEntity = addressRepository.save(updateEntity);

        return  modelMapper.map(savedEntity, AddressSearchDTO.class);
    }

    public void delete(Integer id) {
        addressRepository.deleteById(id);
    }
}