package com.project.webapp.film.repository;

import com.project.webapp.film.entity.FilmActor;
import com.project.webapp.film.entity.FilmActorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmActorRepository extends JpaRepository<FilmActor, FilmActorId> {
}
