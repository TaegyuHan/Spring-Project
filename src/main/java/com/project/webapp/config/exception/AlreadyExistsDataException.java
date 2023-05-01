package com.project.webapp.config.exception;

import lombok.Getter;

@Getter
public class AlreadyExistsDataException extends ExistsDataException {

    public AlreadyExistsDataException(String message) {
        super(message);
    }

    public AlreadyExistsDataException(String message, Object data) {
        super(message, data);
    }
}