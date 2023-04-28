package com.project.webapp.area.entity;

public class CountryTest {

    public static Country buildCountry() {
        return Country.builder()
                .country("Afghanistan")
                .build();
    }
}