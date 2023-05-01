package com.project.webapp.config;

import com.project.webapp.config.dto.ErrorMessageDTO;
import com.project.webapp.config.dto.FieldErrorDTO;
import com.project.webapp.config.exception.AlreadyExistsDataException;
import com.project.webapp.config.exception.InvalidDtoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.ConnectException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // DTO check
    @ExceptionHandler(InvalidDtoException.class)
    public ResponseEntity<ErrorMessageDTO> dtoValidatedException(InvalidDtoException ex, WebRequest request) {

        ErrorMessageDTO dto = ErrorMessageDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .data(fieldErrorToDTO(ex.getFieldErrors()))
                .description(request.getDescription(false))
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDTO> dtoValidatedException(MethodArgumentNotValidException ex, WebRequest request) {

        ErrorMessageDTO dto = ErrorMessageDTO.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message("Please enter the data correctly")
                .data(fieldErrorToDTO(ex.getFieldErrors()))
                .description(request.getDescription(false))
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    // 기존에 존재하는 데이터
    @ExceptionHandler(AlreadyExistsDataException.class)
    public ResponseEntity<ErrorMessageDTO> duplicateDataException(AlreadyExistsDataException ex, WebRequest request) {
        ErrorMessageDTO dto = ErrorMessageDTO.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .data(ex.getData())
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(dto, HttpStatus.CONFLICT);
    }

    // DB 접속 에러
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ErrorMessageDTO> connectException(ConnectException ex, WebRequest request) {
        ErrorMessageDTO dto = ErrorMessageDTO.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<FieldErrorDTO> fieldErrorToDTO(List<FieldError> fieldErrors) {
        return fieldErrors.stream()
                .map(error -> FieldErrorDTO.builder()
                        .field(error.getField())
                        .errorMessage(error.getDefaultMessage())
                        .rejectedValue(error.getRejectedValue())
                        .build())
                .collect(Collectors.toList());
    }
}