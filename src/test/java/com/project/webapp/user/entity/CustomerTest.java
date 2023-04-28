package com.project.webapp.user.entity;

import com.project.webapp.area.entity.Address;
import com.project.webapp.store.entity.Store;

public class CustomerTest {

    public static Customer buildCustomer(Address address, Store store) {
        return Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .address(address)
                .store(store)
                .active(true)
                .build();
    }
}