package com.project.webapp.film.service;

import com.project.webapp.config.exception.NonExistentDataException;
import com.project.webapp.film.dto.CategorySaveDTO;
import com.project.webapp.film.dto.CategorySearchDTO;
import com.project.webapp.film.entity.Category;
import com.project.webapp.film.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategorySearchDTO create(CategorySaveDTO categorySaveDTO) {
        Category newEntity = modelMapper.map(categorySaveDTO, Category.class);
        Category savedEntity = categoryRepository.save(newEntity);
        return modelMapper.map(savedEntity, CategorySearchDTO.class);
    }

    @Transactional(readOnly = true)
    public CategorySearchDTO findByid(Integer id) {

        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }

        return modelMapper.map(category, CategorySearchDTO.class);
    }

    public CategorySearchDTO update(Integer id, CategorySaveDTO categorySaveDTO) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }
        Category updateEntity = category.get();

        updateEntity.setName(categorySaveDTO.getName());
        Category savedEntity = categoryRepository.save(updateEntity);
        return modelMapper.map(savedEntity, CategorySearchDTO.class);
    }

    public void delete(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new NonExistentDataException("Data does not exist.", id);
        }
        categoryRepository.deleteById(id);
    }
}