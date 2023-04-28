package com.project.webapp.store.entity;

import com.project.webapp.film.entity.Film;

public class InventoryTest {

    public static Inventory buildInventory(Film Film, Store store) {
        return Inventory.builder()
                .film(Film)
                .store(store)
                .build();
    }
}