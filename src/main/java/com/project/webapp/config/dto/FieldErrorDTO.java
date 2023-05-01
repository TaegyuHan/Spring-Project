package com.project.webapp.config.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FieldErrorDTO {
    private String field;
    private String errorMessage;
    private Object rejectedValue;
}