package com.essential.app.exception;

import com.essential.app.resource.error.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity(apiError, apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex));
    }


    @ExceptionHandler(value={InvalidDataAccessApiUsageException.class})
    protected ResponseEntity<Object> invalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return this.buildResponseEntity(apiError);
    }

    @ExceptionHandler(value={EntityNotFoundException.class})
    protected ResponseEntity<Object> entityNotFoundHandle(EntityNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return this.buildResponseEntity(apiError);
    }

    @ExceptionHandler(value={DuplicateException.class})
    protected ResponseEntity<Object> duplicateHandle(DuplicateException ex) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT);
        apiError.setMessage(ex.getMessage());
        return this.buildResponseEntity(apiError);
    }

    @ExceptionHandler(value={BusinessException.class})
    protected ResponseEntity<Object> businessException(BusinessException ex) {
        return this.buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(value={DataIntegrityViolationException.class})
    protected ResponseEntity<Object> duplicateHandle(DataIntegrityViolationException ex) {
        return this.buildResponseEntity(new ApiError(HttpStatus.CONFLICT, "Violation of an integrity constraint."));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, errors));
    }



}

