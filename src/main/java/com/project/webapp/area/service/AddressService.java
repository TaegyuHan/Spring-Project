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

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AddressSearchDTO create(AddressSaveDTO addressSaveDTO) {
        // 도시가 존재하는지 확인
        Optional<City> city = cityRepository.findById(addressSaveDTO.getCityId());
        if (city.isEmpty()) {
            throw new NonExistentDataException("CityId not found", addressSaveDTO);
        }

        modelMapper.typeMap(AddressSaveDTO.class, Address.class).addMappings(mapper -> {
            mapper.skip(Address::setAddressId);
            mapper.map(AddressSaveDTO::getAddress, Address::setAddress);
            mapper.map(AddressSaveDTO::getAddress2, Address::setAddress2);
            mapper.map(AddressSaveDTO::getDistrict, Address::setDistrict);
            mapper.map(AddressSaveDTO::getPostalCode, Address::setPostalCode);
            mapper.map(AddressSaveDTO::getPhone, Address::setPhone);
        });

        Address newEntity = modelMapper.map(addressSaveDTO, Address.class);
        newEntity.setCity(city.get());
        Address savedEntity = addressRepository.save(newEntity);

        return modelMapper.map(savedEntity, AddressSearchDTO.class);
    }

    public AddressSearchDTO findByid(Integer id) {
        // 주소가 존재하는지 확인
        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()) {
            throw new NonExistentDataException("AddressId not found", id);
        }

        return modelMapper.map(address, AddressSearchDTO.class);
    }

    public AddressSearchDTO update(Integer id, AddressSaveDTO addressSaveDTO) {
        // 주소가 기존에 있는지 확인
        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()) {
            throw new NonExistentDataException("AddressId not found", id);
        }

        // 수정하려는 도시가 있는지 확인
        Optional<City> city = cityRepository.findById(addressSaveDTO.getCityId());
        if (city.isEmpty()) {
            throw new NonExistentDataException("CityId not found", addressSaveDTO);
        }
        Address updateEntity = address.get();

        updateEntity.setAddress(addressSaveDTO.getAddress());
        updateEntity.setAddress2(addressSaveDTO.getAddress2());
        updateEntity.setDistrict(addressSaveDTO.getDistrict());
        updateEntity.setCity(city.get());
        updateEntity.setPostalCode(addressSaveDTO.getPostalCode());
        updateEntity.setAddress2(addressSaveDTO.getPhone());
        Address savedEntity = addressRepository.save(updateEntity);

        return  modelMapper.map(savedEntity, AddressSearchDTO.class);
    }

    public void delete(Integer id) {
        if (addressRepository.findById(id).isEmpty()) {
            throw new NonExistentDataException("AddressId not found", id);
        }
        addressRepository.deleteById(id);
    }
}