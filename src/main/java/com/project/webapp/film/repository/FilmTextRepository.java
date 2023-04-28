package com.project.webapp.film.repository;


import com.project.webapp.film.entity.FilmText;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmTextRepository extends JpaRepository<FilmText, Integer> {

    @Modifying
    @Transactional // film_text 테이블 인덱스 생성
    @Query(value = "ALTER TABLE film_text ADD FULLTEXT idx_title_description (title, description);", nativeQuery = true)
    void createFilmTextIndex();

    Optional<FilmText> findByFilmId(Integer filmId);

    Optional<FilmText> findByTitle(String title);

    Optional<FilmText> findByDescription(String description);
}