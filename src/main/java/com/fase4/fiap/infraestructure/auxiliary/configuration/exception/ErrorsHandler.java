package com.fase4.fiap.infraestructure.auxiliary.configuration.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ErrorsHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleError404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationErrorData>> handleError400(MethodArgumentNotValidException ex) {
        List<FieldError> erros = ex.getFieldErrors();
        List<ValidationErrorData> messages = new ArrayList<>(erros.size());

        erros.forEach(erro -> addOrMergeErrorMessage(
                messages,
                erro.getField(),
                erro.getDefaultMessage()
        ));

        return ResponseEntity.badRequest().body(messages);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleError400(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ValidationErrorData>> handleValidationError(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        List<ValidationErrorData> messages = new ArrayList<>(constraintViolations.size());

        constraintViolations.forEach(constraintViolation -> addOrMergeErrorMessage(
                messages,
                constraintViolation.getPropertyPath().toString(),
                constraintViolation.getMessage()
        ));

        return ResponseEntity.badRequest().body(messages);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleError500(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro: " + ex.getLocalizedMessage());
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<String> handleError500(JpaSystemException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro: " + ex.getLocalizedMessage());
    }

    private void addOrMergeErrorMessage(List<ValidationErrorData> messages, String field, String message) {
        messages.stream()
                .filter(data -> Objects.equals(data.field(), field))
                .findFirst()
                .ifPresentOrElse(
                        existingData -> {
                            messages.remove(existingData);
                            existingData.messages().add(message);
                            messages.add(existingData);
                        },
                        () -> messages.add(new ValidationErrorData(field, new ArrayList<>(Collections.singletonList(message))))
                );
    }

    private record ValidationErrorData(String field, List<String> messages) {
    }
}
