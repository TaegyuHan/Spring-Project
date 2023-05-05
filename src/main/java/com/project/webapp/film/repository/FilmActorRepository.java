package com.project.webapp.film.repository;

import com.project.webapp.film.entity.FilmActor;
import com.project.webapp.film.entity.FilmActorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmActorRepository extends JpaRepository<FilmActor, FilmActorId> {
    List<FilmActor> findById_FilmId(Integer id);

    void deleteById_FilmId(Integer id);
}
