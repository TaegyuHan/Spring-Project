package com.project.webapp.film.entity;

public class ActorTest {

    public static Actor buildActor() {
        return Actor.builder()
                .firstName("NICK")
                .lastName("WAHLBERG")
                .build();
    }
}