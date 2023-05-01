package com.project.webapp.config.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ResponseDTO<T> {
    private String message;
    private int statusCode;
    private Instant timestamp;

    public ResponseDTO(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = Instant.now();
    }
}
