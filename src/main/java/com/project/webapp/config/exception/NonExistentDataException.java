package com.project.webapp.config.exception;

import lombok.Getter;

@Getter
public class NonExistentDataException extends ExistsDataException {

    public NonExistentDataException(String message) {
        super(message);
    }

    public NonExistentDataException(String message, Object data) {
        super(message, data);
    }
}