package com.project.webapp.area.entity;

public class AddressTest {

    public static Address buildAddress(City city) {
        return Address.builder()
                .address("address Test Data")
                .address2("address2 Test Data")
                .district("district Test Data")
                .city(city)
                .postalCode("12345")
                .phone("555-1234")
                .build();
    }
}