package com.project.webapp.film.repository;


import com.project.webapp.film.entity.Actor;
import com.project.webapp.film.entity.ActorTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ActorRepositoryTest {

    @Autowired
    private ActorRepository actorRepository;

    @Test
    void create() {
        // Given
        Actor newActor = ActorTest.buildActor();

        // When
        Actor savedActor = actorRepository.save(newActor);

        // Then
        Optional<Actor> findByIdOptional = actorRepository.findById(newActor.getActorId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedActor);
    }

    @Test
    public void read() {
        // Given
        Actor newActor = ActorTest.buildActor();
        Actor savedActor = actorRepository.save(newActor);

        // When
        Optional<Actor> findByIdOptional = actorRepository.findById(savedActor.getActorId());
        Optional<Actor> findByFirstNameOptional = actorRepository.findByFirstName(savedActor.getFirstName());
        Optional<Actor> findByLastNameOptional = actorRepository.findByLastName(savedActor.getLastName());

        // Then
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedActor);

        assertThat(findByFirstNameOptional).isPresent();
        assertThat(findByFirstNameOptional.get()).isEqualTo(savedActor);

        assertThat(findByLastNameOptional).isPresent();
        assertThat(findByLastNameOptional.get()).isEqualTo(savedActor);
    }

    @Test
    void update() {
        // Given
        Actor newActor = ActorTest.buildActor();
        Actor savedActor = actorRepository.save(newActor);

        // When
        savedActor.setFirstName("NEW_FIRST_NAME");
        savedActor.setLastName("NEW_LAST_NAME");
        actorRepository.save(savedActor);

        // Then
        Optional<Actor> findByIdOptional = actorRepository.findById(savedActor.getActorId());
        assertThat(findByIdOptional).isPresent();
        assertThat(findByIdOptional.get()).isEqualTo(savedActor);
    }

    @Test
    public void delete() {
        // Given
        Actor newActor = ActorTest.buildActor();
        Actor savedActor = actorRepository.save(newActor);

        // When
        actorRepository.delete(savedActor);

        // Then
        Optional<Actor> findByIdOptional = actorRepository.findById(savedActor.getActorId());
        assertThat(findByIdOptional).isNotPresent();
    }
}