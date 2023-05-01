package com.project.webapp.config.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@NoArgsConstructor
public class InvalidDtoException extends RuntimeException {

    private List<FieldError> fieldErrors;

    public InvalidDtoException(String message) {
        super(message);
    }

    public InvalidDtoException(String message, BindingResult bindingResult) {
        super(message);
        this.fieldErrors = bindingResult.getFieldErrors();
    }
}