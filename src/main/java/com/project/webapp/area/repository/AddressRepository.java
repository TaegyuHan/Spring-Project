package com.project.webapp.area.repository;


import com.project.webapp.area.entity.Address;
import com.project.webapp.area.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<Address> findByAddress(String address);

    Optional<Address> findByAddress2(String address2);

    Optional<Address> findByDistrict(String district);

    Optional<Address> findByCity(City city);

    Optional<Address> findByPostalCode(String postalCode);

    Optional<Address> findByPhone(String phone);
}