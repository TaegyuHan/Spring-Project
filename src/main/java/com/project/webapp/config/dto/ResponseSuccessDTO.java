package com.project.webapp.config.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseSuccessDTO<T> extends ResponseDTO<T> {

    private T data;

    public ResponseSuccessDTO(int statusCode, T data) {
        super("success", statusCode);
        this.data = data;
    }

    public ResponseSuccessDTO(int statusCode) {
        super("success", statusCode);
    }
}