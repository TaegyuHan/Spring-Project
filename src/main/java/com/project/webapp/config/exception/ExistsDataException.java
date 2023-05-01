package com.project.webapp.config.exception;

import lombok.Getter;

@Getter
public class ExistsDataException extends RuntimeException {
    private Object data;

    public ExistsDataException(String message) {
        super(message);
    }

    public ExistsDataException(String message, Object data) {
        super(message);
        this.data = data;
    }
}