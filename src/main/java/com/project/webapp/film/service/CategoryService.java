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
    public CategorySearchDTO findById(Integer id) {
        return categoryRepository.findById(id)
                .map(category -> modelMapper.map(category, CategorySearchDTO.class))
                .orElseThrow(() -> new NonExistentDataException("Data does not exist.", id));
    }

    public CategorySearchDTO update(Integer id, CategorySaveDTO categorySaveDTO) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NonExistentDataException("Data does not exist.", id));

        category.setName(categorySaveDTO.getName());
        Category savedEntity = categoryRepository.save(category);
        return modelMapper.map(savedEntity, CategorySearchDTO.class);
    }

    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }
}