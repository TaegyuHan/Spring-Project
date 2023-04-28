package com.project.webapp.area.entity;

public class CityTest {

    public static City buildCity(Country country) {
        return City.builder()
                .city("Abu Dhabi")
                .country(country)
                .build();
    }
}