package com.project.webapp.user.entity;

import com.project.webapp.area.entity.Address;
import com.project.webapp.store.entity.Store;

public class StaffTest {

    public static Staff buildStaff(Address address, Store store) {
        return Staff.builder()
                .firstName("John")
                .lastName("Doe")
                .address(address)
                .email("johndoe@example.com")
                .store(store)
                .active(true)
                .username("johndoe")
                .password("password")
                .build();
    }
}