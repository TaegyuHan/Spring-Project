package com.project.webapp.store.entity;

import com.project.webapp.area.entity.Address;

public class StoreTest {

    public static Store buildStore(Address address) {
        return Store.builder()
                .address(address)
                .build();
    }
}