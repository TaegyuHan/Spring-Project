package com.project.webapp.film.repository;

import com.project.webapp.film.entity.FilmCategory;
import com.project.webapp.film.entity.FilmCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmCategoryRepository extends JpaRepository<FilmCategory, FilmCategoryId> {

}