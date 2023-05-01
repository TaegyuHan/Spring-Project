package com.project.webapp.config.exception;

import lombok.Getter;

@Getter
public class AlreadyExistsDataException extends RuntimeException {
    private Object data;

    public AlreadyExistsDataException(String message) {
        super(message);
    }

    public AlreadyExistsDataException(String message, Object data) {
        super(message);
        this.data = data;
    }
}