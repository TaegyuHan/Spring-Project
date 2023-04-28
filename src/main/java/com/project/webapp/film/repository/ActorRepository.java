package com.project.webapp.film.repository;

import com.project.webapp.film.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
    Optional<Actor> findByFirstName(String firstName);

    Optional<Actor> findByLastName(String lastName);
}