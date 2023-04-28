package com.project.webapp.film.entity;

public class CategoryTest {

    public static Category buildCategory() {
        return Category.builder()
                .name("Animation")
                .build();
    }
}