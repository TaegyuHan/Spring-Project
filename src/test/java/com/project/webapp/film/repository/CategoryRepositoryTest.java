package com.project.webapp.film.repository;


import com.project.webapp.film.entity.Category;
import com.project.webapp.film.entity.CategoryTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void create() {
        // Given
        Category newCategory = CategoryTest.buildCategory();

        // When
        Category savedCategory = categoryRepository.save(newCategory);

        // Then
        Optional<Category> findByIdOptional = categoryRepository.findById(savedCategory.getCategoryId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedCategory);
    }

    @Test
    public void read() {
        // Given
        Category newCategory = CategoryTest.buildCategory();
        Category savedCategory = categoryRepository.save(newCategory);

        // When
        Optional<Category> findByIdOptional = categoryRepository.findById(savedCategory.getCategoryId());
        Optional<Category> findByNameOptional = categoryRepository.findByName(newCategory.getName());

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedCategory);

        assertThat(findByNameOptional).isPresent();
        assertThat(findByNameOptional.get()).isEqualTo(savedCategory);
    }

    @Test
    public void update() {
        // Given
        Category newCategory = CategoryTest.buildCategory();
        Category savedCategory = categoryRepository.save(newCategory);

        // When
        savedCategory.setName("Adventure");
        categoryRepository.save(savedCategory);

        // Then
        Optional<Category> findByIdOptional = categoryRepository.findById(savedCategory.getCategoryId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedCategory);
    }

    @Test
    void delete() {
        // Given
        Category newCategory = CategoryTest.buildCategory();
        Category savedCategory = categoryRepository.save(newCategory);

        // When
        categoryRepository.delete(savedCategory);

        // Then
        Optional<Category> findByIdOptional = categoryRepository.findById(savedCategory.getCategoryId());
        assertThat(findByIdOptional).isNotPresent();
    }
}