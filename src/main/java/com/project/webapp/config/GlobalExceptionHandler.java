package com.project.webapp.config;

import com.project.webapp.config.dto.ErrorMessageDTO;
import com.project.webapp.config.dto.FieldErrorDTO;
import com.project.webapp.config.exception.AlreadyExistsDataException;
import com.project.webapp.config.exception.InvalidDtoException;
import com.project.webapp.config.exception.NonExistentDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.ConnectException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // DTO check
    @ExceptionHandler(InvalidDtoException.class)
    public ResponseEntity<ErrorMessageDTO> dtoValidatedException(InvalidDtoException ex, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorMessageDTO dto = ErrorMessageDTO.builder()
                .statusCode(httpStatus.value())
                .message(ex.getMessage())
                .data(fieldErrorToDTO(ex.getFieldErrors()))
                .description(request.getDescription(false))
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(dto, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDTO> dtoValidatedException(MethodArgumentNotValidException ex, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorMessageDTO dto = ErrorMessageDTO.builder()
                .statusCode(httpStatus.value())
                .message("Please enter the data correctly")
                .data(fieldErrorToDTO(ex.getFieldErrors()))
                .description(request.getDescription(false))
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(dto, httpStatus);
    }

    // 존재하는 데이터
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorMessageDTO> sQLIntegrityConstraintViolationException (SQLIntegrityConstraintViolationException ex, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.CONFLICT;

        ErrorMessageDTO dto = ErrorMessageDTO.builder()
                .statusCode(httpStatus.value())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .data(ex.getErrorCode())
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(dto, httpStatus);
    }

    @ExceptionHandler(AlreadyExistsDataException.class)
    public ResponseEntity<ErrorMessageDTO> alreadyExistsDataException(AlreadyExistsDataException ex, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.CONFLICT;

        ErrorMessageDTO dto = ErrorMessageDTO.builder()
                .statusCode(httpStatus.value())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .data(ex.getData())
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(dto, httpStatus);
    }

    // 존재하지 않는 데이터
    @ExceptionHandler(NonExistentDataException.class)
    public ResponseEntity<ErrorMessageDTO> nonExistentDataException(NonExistentDataException ex, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.NO_CONTENT;

        ErrorMessageDTO dto = ErrorMessageDTO.builder()
                .statusCode(httpStatus.value())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .data(ex.getData())
                .timestamp(Instant.now())
                .build();
        return new ResponseEntity<>(dto, httpStatus);
    }

    // DB 접속 에러
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<ErrorMessageDTO> connectException(ConnectException ex, WebRequest request) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorMessageDTO dto = ErrorMessageDTO.builder()
                .statusCode(httpStatus.value())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .description(request.getDescription(false))
                .build();
        return new ResponseEntity<>(dto, httpStatus);
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