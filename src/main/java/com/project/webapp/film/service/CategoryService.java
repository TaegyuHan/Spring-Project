package com.project.webapp.film.service;

import com.project.webapp.config.exception.AlreadyExistsDataException;
import com.project.webapp.config.exception.NonExistentDataException;
import com.project.webapp.film.dto.CategorySaveDTO;
import com.project.webapp.film.dto.CategorySearchDTO;
import com.project.webapp.film.entity.Category;
import com.project.webapp.film.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CategorySearchDTO create(CategorySaveDTO categorySaveDTO) {
        // 기존에 있는지 확인
        if (categoryRepository.findByName(categorySaveDTO.getName()).isPresent()) {
            throw new AlreadyExistsDataException("The name of the category already exists.", categorySaveDTO);
        }

        Category newEntity = modelMapper.map(categorySaveDTO, Category.class);
        Category savedEntity = categoryRepository.save(newEntity);
        return modelMapper.map(savedEntity, CategorySearchDTO.class);
    }

    public CategorySearchDTO findByid(Integer id) {
        // 존재 유무 확인
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }

        return modelMapper.map(category, CategorySearchDTO.class);
    }

    public CategorySearchDTO update(Integer id, CategorySaveDTO categorySaveDTO) {
        Optional<Category> check;
        // 업데이트 데이터 존재 확인
        check = categoryRepository.findByName(categorySaveDTO.getName());
        if (check.isPresent()) {
            throw new AlreadyExistsDataException("Data already exists.", categorySaveDTO);
        }

        // 데이터 존재 하는지 확인
        check = categoryRepository.findById(id);
        if (check.isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }

        Category updateEntity = check.get();
        updateEntity.setName(categorySaveDTO.getName());
        Category savedEntity = categoryRepository.save(updateEntity);
        return modelMapper.map(savedEntity, CategorySearchDTO.class);
    }

    public void delete(Integer id) {
        if (categoryRepository.findById(id).isEmpty()) {
            throw new NonExistentDataException("Data does not exist.", id);
        }
        categoryRepository.deleteById(id);
    }
}