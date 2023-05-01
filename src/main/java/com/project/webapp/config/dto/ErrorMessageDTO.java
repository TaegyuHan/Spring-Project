package com.project.webapp.config.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessageDTO {
    private int statusCode;
    private Instant timestamp;
    private String message;
    private String description;
    private Object data;
}