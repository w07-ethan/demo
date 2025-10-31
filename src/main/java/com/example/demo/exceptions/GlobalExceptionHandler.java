package com.example.demo.exceptions;

import com.example.demo.dto.response.AppVo;
import com.w07.extn.payment.paypal.exception.PaypalBusinessException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Handles: Validation errors (@Valid)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AppVo<Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        AppVo<Object> apiResponse = AppVo.error(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                errors
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Returns: HTTP 409 Conflict
     */
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<AppVo<Object>> handleResourceAlreadyExists(
            ResourceAlreadyExistsException ex, WebRequest request) {

        AppVo<Object> apiResponse = AppVo.error(
                HttpStatus.CONFLICT.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AppVo<Object>> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {

        AppVo<Object> apiResponse = AppVo.error(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles: Custom business exceptions for Paypal
     */
    @ExceptionHandler(PaypalBusinessException.class)
    public ResponseEntity<AppVo<Object>> handlePaypalBusinessException(
            PaypalBusinessException ex) {

        AppVo<Object> apiResponse = AppVo.error(
                ex.getErrorCode(),
                ex.getMessage()
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Handles: All other unexpected errors (e.g., NullPointerException)
     * Returns: HTTP 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<AppVo<Object>> handleAllUncaughtExceptions(Exception ex) {
        AppVo<Object> apiResponse = AppVo.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred"
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
